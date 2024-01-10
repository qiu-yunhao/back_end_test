package com.example.demo.Controller;

import com.example.demo.Bean.Users;
import com.example.demo.Mapper.MaintenanceInfoMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Util.RCUtil;
import com.example.demo.Answer.Result;
import com.example.demo.Bean.MaintenanceInfo;
import com.example.demo.Dao.MaintenanceDao;
import com.example.demo.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/maintenance")
@RestController
public class MaintenanceController extends BaseController {
    @Autowired
    private MaintenanceDao dao;

    @GetMapping("/getInfo")
    public String getInfo(@RequestHeader("token") String token) {
        return withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            List<MaintenanceInfo> list = dao.getMaintenanceInfoByUid(uid);
            return new Result<>(RCUtil.getSuccess(), list.toString()).toString();
        });
    }

    @DeleteMapping("/deleteInfo")
    public String deleteInfo(@RequestHeader("token") String token, @RequestParam("mid") int mid) {
        return withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            int type = TokenService.getUserTypeFromToken(token);
            boolean flag = MaintenanceInfoMapper.getInstance(dao).isBelongUser(uid, mid, type);
            if (flag) {
                dao.deleteById(mid);
                return new Result<>(RCUtil.getSuccess(), "删除操作已成功执行").toString();
            } else {
                return new Result<>(RCUtil.getNPE(), "该用户下未查询到相关记录信息").toString();
            }

        });
    }

    @GetMapping("/getSingleInfo")
    public String getSingleInfo(@RequestHeader("token") String token, @RequestParam("mid") int mid) {
        return withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            MaintenanceInfoMapper mapper = MaintenanceInfoMapper.getInstance(dao);
            MaintenanceInfo info = mapper.getSingleInfo(mid);
            boolean flag = mapper.isBelongUser(uid, mid, 0);
            if (flag) {
                return new Result<>(RCUtil.getSuccess(), info.toString()).toString();
            } else {
                return new Result<>(RCUtil.getNPE(), "该用户下未查询到相关记录信息").toString();
            }

        });
    }

    @PostMapping("/addInfo")
    public String insertMaintenance(@RequestBody MaintenanceInfo info, @RequestHeader("token") String token) {
        return withTokenVerity(token, () -> withTokenVerity(token, () -> {
            MaintenanceInfoMapper.getInstance(dao).saveEntity(info);
            return new Result<>(RCUtil.getSuccess(), "添加信息成功").toString();
        }));
    }

    @PutMapping("/updateInfo")
    public String updateMaintenance(@RequestHeader("token") String token, @RequestBody MaintenanceInfo info) {
        return withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            int type = TokenService.getUserTypeFromToken(token);
            MaintenanceInfoMapper mapper = MaintenanceInfoMapper.getInstance(dao);
            boolean flag = mapper.isBelongUser(uid, info.getMid(), type);
            if (flag) {
                MaintenanceInfo tar = mapper.getSingleInfo(info.getMid());
                tar.clone(info);
                mapper.saveEntity(tar);
                return new Result<>(RCUtil.getSuccess(), "成功更新相关维修记录信息").toString();
            } else {
                return new Result<>(RCUtil.getNRE(), "相关用户权限错误").toString();
            }
        });
    }

    @PostMapping("/getAllMaintenance")
    public String getAllMaintenance(@RequestHeader("token") String token, @RequestParam("pageNumber") int number, @RequestParam("pageSize") int size, @RequestBody MaintenanceInfo info) {
        return withTokenVerity(token, () -> {
            int type = TokenService.getUserTypeFromToken(token);
            int uid = TokenService.getIdFromToken(token);
            MaintenanceInfoMapper mapper = MaintenanceInfoMapper.getInstance(dao);
            Page<MaintenanceInfo> infos = mapper.searchAllMaintenances(info, number - 1, size, type, uid);
            return new Result<>(RCUtil.getSuccess(), infos).toString();
        });
    }

    @GetMapping("/getMaintencancs")
    public String getMaintenanceById(@RequestHeader("token") String token) {
        return withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            MaintenanceInfoMapper mapper = MaintenanceInfoMapper.getInstance(dao);
            return new Result<>(RCUtil.getSuccess(), mapper.getInfosByUID(uid)).toString();
        });
    }

}
