package com.example.demo.Controller;


import com.example.demo.Bean.Token;
import com.example.demo.Mapper.UserInfoMapper;
import com.example.demo.Util.JsonUtil;
import com.example.demo.Util.RCUtil;
import com.example.demo.Answer.Result;
import com.example.demo.Bean.UserInfo;
import com.example.demo.Bean.Users;
import com.example.demo.Dao.UserInfoDao;
import com.example.demo.Dao.UsersDao;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    @Autowired
    private UsersDao dao;

    @Autowired
    private UserInfoDao infoDao;

    @GetMapping("/findUser")
    String getUsers(@RequestHeader("token") String token) {
        boolean res = TokenService.verityToken(token);
        if (!res) {
            return new Result<>(RCUtil.getTokenErr(), "凭证过期，请重新登录").toString();
        } else {
            int id = TokenService.getIdFromToken(token);
            Users u = UserMapper.getInstance(dao).getUserById(id);
            if (u == null) {
                return new Result<>(RCUtil.getNPE(), "未查询到用户有效信息").toString();
            } else {
                u.setPassword("");
                return new Result<>(RCUtil.getSuccess(), u).toString();
            }
        }
    }

    @PostMapping("/login")
    String login(@RequestBody Users user) {
        Users u = UserMapper.getInstance(dao).getUserByName(user.getUsername());
        if (u == null) {
            return new Result<>(RCUtil.getFailed(), "用户名输入错误").toString();
        } else {
            if (!user.getPassword().equals(u.getPassword())) {
                return new Result<>(RCUtil.getFailed(), "用户名与密码不匹配").toString();
            } else {
                return new Result<>(RCUtil.getSuccess(), new Token(TokenService.getToken(u.getId(), u.getType()))).toString();
            }
        }
    }

    @PostMapping("/register")
    @ResponseBody
    String AddUsers(@RequestBody Users u) {
        try {
            Users tar = UserMapper.getInstance(dao).getUserByName(u.getUsername());
            if (tar == null || u.getUsername().isEmpty() || u.getPassword().isEmpty()) {
                Users user = dao.save(u);
                UserInfo info = new UserInfo();
                info.setUid(user.getId());
                info.setName(u.getUsername());
                infoDao.save(info);
                return new Result<>(RCUtil.getSuccess(), "注册成功").toString();
            } else {
                return new Result<>(RCUtil.getExistErr(), "该用户已经注册过了，请更换用户名").toString();
            }
        } catch (Exception e) {
            return new Result<>(RCUtil.getNPE(), e.toString()).toString();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    String update(@RequestBody Users u, @RequestHeader(name = "token") String token) {
        return withTokenVerity(token, () -> {
            int type = TokenService.getUserTypeFromToken(token);
            boolean flag = type == 1;
            if (u.getUsername().isEmpty()) {
                return new Result<>(RCUtil.getFailed(), "请求参数需要你加上username!").toString();
            }
            Users tarUser = UserMapper.getInstance(dao).getUserByName(u.getUsername());
            if (!flag) {
                int uid = TokenService.getIdFromToken(token);
                tarUser = UserMapper.getInstance(dao).getUserById(uid);
            }
            tarUser.clone(u);
            UserMapper.getInstance(dao).saveEntity(tarUser);
            return new Result<>(RCUtil.getSuccess(), "修改信息成功").toString();

        });
    }

    @ResponseBody
    @DeleteMapping("/deleteUsers")
    public String deleteUser(@RequestHeader("token") String token, @RequestParam("uid") int id) {
        return withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            int type = TokenService.getUserTypeFromToken(token);
            boolean flag = type == 1 || uid == id;
            if (flag) {
                dao.deleteById(id);
                return new Result<>(RCUtil.getSuccess(), "删除操作已成功执行").toString();
            } else {
                return new Result<>(RCUtil.getNPE(), "该用户下未查询到相关记录信息").toString();
            }

        });
    }


    @PostMapping("/getAllUser")
    public String getAllUsers(@RequestHeader("token") String token, @RequestParam("pageNumber") int number, @RequestParam("pageSize") int size, @RequestBody Users u) {
        return withTokenVerity(token, () -> {
            int type = TokenService.getUserTypeFromToken(token);
            if (type == 0) {
                return new Result<>(RCUtil.getSuccess(), "用户权限不足").toString();
            } else {
                Page<Users> infos = UserMapper.getInstance(dao).searchAllEntities(u, number - 1, size);
                return new Result<>(RCUtil.getSuccess(), infos).toString();
            }
        });
    }


}

