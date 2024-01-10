package com.example.demo.Controller;

import com.example.demo.Dao.UsersDao;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Util.RCUtil;
import com.example.demo.Answer.Result;
import com.example.demo.Bean.Image;
import com.example.demo.Bean.UserInfo;
import com.example.demo.Dao.ImageDao;
import com.example.demo.Dao.UserInfoDao;
import com.example.demo.Mapper.ImageMapper;
import com.example.demo.Mapper.UserInfoMapper;
import com.example.demo.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@CrossOrigin
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController{

    private static final String filePath = "D:\\Program Files (x86)\\java\\images";

    @Autowired
    private UserInfoDao infoDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UsersDao usersDao;

    @DeleteMapping("/delete")
    public String deleteUserInfo(@RequestHeader("token") String token, @RequestParam("infoId") int id) {
        return withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            int type = TokenService.getUserTypeFromToken(token);
            boolean flag = type == 1 || uid == id;
            if (flag) {
                UserInfoMapper infoMapper = UserInfoMapper.getInstance(infoDao);
                UserInfo tar = infoMapper.getUserInfoByID(id);
                infoMapper.deleteInfo(tar);
                UserMapper mapper = UserMapper.getInstance(usersDao);
                mapper.deleteEntity(mapper.getUserById(tar.getUid()));
                return new Result<>(RCUtil.getSuccess(),"删除操作已成功执行").toString();
            } else {
                return new Result<>(RCUtil.getNPE(),"该用户下未查询到相关记录信息").toString();
            }
        });
    }
    

    @PutMapping("/updateInfo")
    public String addUserInfo(@RequestHeader("token") String token, @RequestBody UserInfo info) {
        return withTokenVerity(token, ()-> {
            int uid = TokenService.getIdFromToken(token);
            int type = TokenService.getIdFromToken(token);
            boolean flag = type == 1;
            UserInfo tarInfo = UserInfoMapper.getInstance(infoDao).getUserInfoByUname(info.getName());
            if (!flag) {
                tarInfo = UserInfoMapper.getInstance(infoDao).getUserInfoByUID(uid);
            }
            if (tarInfo != null) {
                tarInfo.clone(info);
                UserInfoMapper.getInstance(infoDao).saveEntity(tarInfo);
                return new Result<>(RCUtil.getSuccess(), "信息修改成功").toString();
            } else {
                return new Result<>(RCUtil.getNRE(),"未查到对应信息").toString();
            }

        });
    }

    @PostMapping("/image")
    public String setImage(@RequestParam("file") MultipartFile file, @RequestHeader("token") String token) {
        return withTokenVerity(token, ()-> {
            int id = TokenService.getIdFromToken(token);
            Image image = new Image(file ,filePath);
            UserInfo info = UserInfoMapper.getInstance(infoDao).getUserInfoByUID(id);
            try {
                File want = new File(image.getPath());
                if (!want.getParentFile().exists()) {
                    want.getParentFile().mkdirs();
                }
                imageDao.save(image);
                file.transferTo(want);
                Image need = ImageMapper.getINSTANCE(imageDao).getImageByPath(image.getPath());
                if (need != null) {
                    info.setPid(need.getPid());
                    UserInfoMapper.getInstance(infoDao).saveEntity(info);
                } else {
                    return new Result<>(RCUtil.getNPE(),"路径错误").toString();
                }

            } catch (IOException e) {
                return new Result<>(RCUtil.getNRE(), e).toString();

            }
            return new Result<>(RCUtil.getSuccess(),"操作成功").toString();
        });
    }


    @GetMapping(value = "/getImage", produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    public String getImage(@RequestHeader("token") String token) {
        return withTokenVerity(token,()-> {
            int uid = TokenService.getIdFromToken(token);
            ImageMapper imageMapper = ImageMapper.getINSTANCE(imageDao);
            UserInfoMapper infoMapper = UserInfoMapper.getInstance(infoDao);
            UserInfo info = infoMapper.getUserInfoByUID(uid);
            Image image = imageMapper.getImageByID(info.getPid());
            try {
                File file = new File(image.getPath());
                if (!file.exists()) {
                    return new Result<>(RCUtil.getNPE(), "未查询到相关图片").toString();
                }
                byte[] bytes = Files.readAllBytes(file.toPath());
                return new Result<>(RCUtil.getSuccess(), bytes).toString();
            } catch (IOException e) {
                return new Result<>(RCUtil.getNRE(), e).toString();
            }
        });
    }


    @GetMapping("/getInfo")
    public String getUserInfo(@RequestHeader("token") String token) {
        return withTokenVerity(token, ()-> {
            int uid = TokenService.getIdFromToken(token);
            UserInfo u = UserInfoMapper.getInstance(infoDao).getUserInfoByUID(uid);
            return new Result<>(RCUtil.getSuccess(), u).toString();
        });
    }

    @PostMapping("/getAllUserInfo")
    public String getUserInfos(@RequestHeader("token") String token,@RequestParam("pageNumber") int number, @RequestParam("pageSize") int size,@RequestBody UserInfo info) {
        return withTokenVerity(token, () -> {
            int type = TokenService.getUserTypeFromToken(token);
            if (type == 0) {
                return new Result<>(RCUtil.getSuccess(), "用户权限不足").toString();
            } else {
                Page<UserInfo> infos = UserInfoMapper.getInstance(infoDao).searchAllEntities(info,number -1, size);
                return new Result<>(RCUtil.getSuccess(), infos).toString();
            }
        });
    }

}
