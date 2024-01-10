package com.example.demo.Controller;

import com.example.demo.Bean.UserInfo;
import com.example.demo.Mapper.UserInfoMapper;
import com.example.demo.Util.RCUtil;
import com.example.demo.Answer.Result;
import com.example.demo.Bean.Car;
import com.example.demo.Dao.CarDao;
import com.example.demo.Mapper.CarMapper;
import com.example.demo.Token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/car")
public class CarController extends BaseController {

    @Autowired
    private CarDao carDao;


    @GetMapping("/searchCar")
    public String findCar(@RequestHeader("token") String token) {
        String ans = withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            List<Car> cars = CarMapper.getInstance(carDao).getCarByUser(uid);
            return new Result<>(RCUtil.getSuccess(), cars).toString();
        });
        return ans;
    }


    @PostMapping("/addCar")
    public String createCar(@RequestHeader("token") String token, @RequestBody Car car) {
        return withTokenVerity(token, () -> {
            int type = TokenService.getUserTypeFromToken(token);
            if (type == 0) {
                int uid = TokenService.getIdFromToken(token);
                car.setUid(uid);
            } else {
                if (car.getUid() == 0) {
                    return new Result<>(RCUtil.getFailed(), "请指定所属用户，使用uid经行指定").toString();
                }
            }
            CarMapper.getInstance(carDao).saveEntity(car);
            return new Result<>(RCUtil.getSuccess(), "相关信息已存入数据库").toString();
        });
    }

    @PutMapping("/updateCar")
    public String updateCar(@RequestHeader("token") String token, @RequestBody Car car) {
        return withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            int uType = TokenService.getUserTypeFromToken(token);
            List<Car> cars = CarMapper.getInstance(carDao).getCarByUser(uid);
            boolean flag = uType == 1;
            for (Car c : cars) {
                if (c.getCid() == car.getCid()) {
                    car.getStoreMessage(c);
                    flag = true;
                }
            }
            if (flag) {
                if (uType == 0 || car.getUid() == 0) {
                    car.setUid(uid);
                }
                System.out.println(car);
                carDao.save(car);
                return new Result<>(RCUtil.getSuccess(), "修改操作成功").toString();
            } else {
                return new Result<>(RCUtil.getNPE(), "该用户下未查询到该车辆").toString();
            }
        });
    }

    @DeleteMapping("deleteCar")
    public String deleteCar(@RequestHeader("token") String token, @RequestParam("cid") int cid) {
        return withTokenVerity(token, () -> {
            int uid = TokenService.getIdFromToken(token);
            int uType = TokenService.getIdFromToken(token);
            Car car = carDao.findById(cid).get();
            boolean flag = uType == 1;
            if (flag || car.getUid() == uid) {
                carDao.delete(car);
                return new Result<>(RCUtil.getSuccess(), "删除操作成功").toString();
            } else {
                return new Result<>(RCUtil.getNPE(), "该用户下未查询到该车辆").toString();
            }
        });
    }

    @PostMapping("/getAllCars")
    public String getAllCars(@RequestHeader("token") String token, @RequestParam("pageNumber") int number, @RequestParam("pageSize") int size, @RequestBody Car car) {
        return withTokenVerity(token, () -> {
            int type = TokenService.getUserTypeFromToken(token);
            int uid = TokenService.getIdFromToken(token);
            if (type == 0) {
                car.setUid(uid);
            }
            Page<Car> infos = CarMapper.getInstance(carDao).searchAllEntities(car, number - 1, size);
            return new Result<>(RCUtil.getSuccess(), infos).toString();
        });
    }

}
