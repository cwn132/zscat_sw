package com.macro.mall.sys.controller;

import com.macro.mall.bo.AdminUserDetails;
import com.macro.mall.model.UmsAdmin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	public UmsAdmin getCurrentUser() {
		try {
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			AdminUserDetails memberDetails = (AdminUserDetails) auth.getPrincipal();
			return memberDetails.getUmsAdmin();
		}catch (Exception e){
			return new UmsAdmin();
		}
	}
}