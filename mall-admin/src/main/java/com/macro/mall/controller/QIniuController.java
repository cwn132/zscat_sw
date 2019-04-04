package com.macro.mall.controller;


import com.macro.mall.dto.CommonResult;
import com.macro.mall.dto.OssCallbackResult;
import com.macro.mall.dto.OssPolicyResult;
import com.macro.mall.service.impl.OssServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 七牛相关操作接口
 * https://gitee.com/zscat-platform/mall on 2018/4/26.
 */
@Controller
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/qiniu")
public class QIniuController {
    @Autowired
    private OssServiceImpl ossService;

    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public Object policy() {
        OssPolicyResult result = ossService.policy();
        return new CommonResult().success(result);
    }

    @ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public Object callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return new CommonResult().success(ossCallbackResult);
    }

    /**
     *
     * @param file
     * @param request
     * @return
     */
   /* @ResponseBody
    @PostMapping("/upload1")
    PhotoResult upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        PhotoResult result = null;
        //设置filename
        // String filename = new Random().nextInt(10000)+file.getOriginalFilename();
        try {
            File files = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator")+file.getOriginalFilename());
            file.transferTo(files);

            result = photoUploadUtil.uploadPhoto(files.getAbsolutePath(), file.getOriginalFilename());
            return result;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return result;
    }*/
}
