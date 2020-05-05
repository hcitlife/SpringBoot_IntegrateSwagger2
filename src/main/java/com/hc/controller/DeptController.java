package com.hc.controller;

import com.hc.model.Dept;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/dept")
@Api(value = "DeptController-部门接口模拟", tags = "DeptController-部门接口模拟")
//@ApiResponses({
//        @ApiResponse(code = 200, message = "OK"),
//        @ApiResponse(code = 400, message = "客户端请求错误"),
//        @ApiResponse(code = 404, message = "找不到路径"),
//        @ApiResponse(code = 500, message = "编译异常")
//})
public class DeptController {

    private List<Dept> deptList;

    {  //初始化
        deptList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Dept dept = new Dept();
            dept.setDeptno(i);
            dept.setDname("dname_" + i);
            dept.setLoc("loc_" + i);
            deptList.add(dept);
        }
    }


//    @PostMapping(value = "/add")
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加部门信息", notes = "", httpMethod = "POST")
    public List<Dept> addDept(@RequestBody Dept dept) throws Exception {
        deptList.add(dept);
        return deptList;
    }


    @RequestMapping(value = "/delete/{deptno}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除部门信息", notes = "")
    //不知道为什么此处写成path会报错
    @ApiImplicitParam(paramType = "delete", name = "deptno", value = "部门ID", required = true, dataType = "int")
    public List<Dept> deleteDept(@PathVariable(value = "deptno") Integer deptno) {
        for (int i = 0; i < deptList.size(); i++) {
            if (deptList.get(i).getDeptno() == deptno) {
                deptList.remove(i);
            }
        }
        return deptList;
    }


    @PutMapping(value = "/updateDept1/{deptno}")
    @ApiOperation(value = "修改部门信息", notes = "", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "deptno", value = "部门ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "dept", value = "用户实体,传入更改后的数据", required = true, dataType = "Dept")
    })
    public List<Dept> updateDept1(@PathVariable(value = "deptno", required = true) Integer deptno, @RequestBody Dept dept) {
        for (int i = 0; i < deptList.size(); i++) {
            if (deptList.get(i).getDeptno() == deptno) {
                deptList.add(i, dept);
                break;
            }
        }
        return deptList;
    }


    @PostMapping("/updateDept2")
    @ApiOperation(value = "修改部门地址", notes = "根据部门编号修改部门地址")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "deptno", value = "部门ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "dname", value = "部门名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "loc", value = "部门地址", required = true, dataType = "String")
    })
    public List<Dept> updateDept2(@RequestParam(value = "deptno") Integer deptno,
                                  @RequestParam(value = "dname") String dname,
                                  @RequestParam(value = "loc") String loc) {
        for (int i = 0; i < deptList.size(); i++) {
            if (deptList.get(i).getDeptno() == deptno) {
                deptList.add(i, new Dept(deptno, dname, loc));
                break;
            }
        }
        return deptList;
    }


    @GetMapping(value = "/list")
    @ApiOperation(value = "获取部门列表", notes = "一次全部取，不分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页数", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页记录数", required = true, dataType = "String"),
    })
    public List<Dept> getDoctorList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageIndex,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) throws RuntimeException {
        return deptList;
    }


    @GetMapping(value = "/getDeptByDeptno1/{deptno}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取部门详细信息", notes = "根据url的deptno来获取部门详细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "deptno", value = "部门ID", required = true, dataType = "Integer", paramType = "path")
    public Dept getDeptByDeptno1(@PathVariable Integer deptno) {
        return deptList.get(deptno);
    }


    @GetMapping(value = "/getDeptByDeptno2/{deptno}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获取部门详细信息", notes = "根据url的deptno来获取部门详细信息", httpMethod = "GET")
    public Dept getDeptByDeptno2(@PathVariable Integer deptno) {
        return deptList.get(deptno);
    }


    @RequestMapping(value = "/getDeptByDeptno3", method = RequestMethod.GET)
    @ApiOperation(value = "获取部门详细信息", notes = "根据id获取部门详细信息")
    @ApiImplicitParam(paramType = "query", name = "deptno", value = "部门ID", required = true, dataType = "Integer")
    public Dept getDeptByDeptno3(@RequestParam Integer deptno) {
        return deptList.get(deptno);
    }

    @ApiIgnore //使用该注解忽略这个API
    @GetMapping(value = "/hi")
    public String  jsonTest() {
        return " hi you!";
    }

}