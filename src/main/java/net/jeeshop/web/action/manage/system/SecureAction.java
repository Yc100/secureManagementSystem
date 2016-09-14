package net.jeeshop.web.action.manage.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.jeeshop.core.Services;
import net.jeeshop.core.system.bean.Menu;
import net.jeeshop.core.system.bean.MenuItem;
import net.jeeshop.core.system.bean.Privilege;
import net.jeeshop.core.system.bean.Secure;
import net.jeeshop.core.system.bean.SecureItem;
import net.jeeshop.services.manage.system.impl.SecureService;
import net.jeeshop.web.action.BaseController;

public class SecureAction extends BaseController<Secure>{
	 @Autowired
	 private SecureService secureService;
	/**
	 * 从PID=0开始加载菜单资源
	 * 获取指定节点的全部子菜单（包括当前菜单节点）
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("getMenusByPid")
    @ResponseBody
//	public String getMenusByPid(HttpServletRequest request) throws Exception {
//		String pid = request.getParameter("pid");
//		if(pid==null || pid.trim().equals(""))
//			pid = "0";
//		String id = request.getParameter("id");
//		List<SecureItem> secures = secureService.loadSecures(null, pid, "#");
//		
//		// 加载全部的菜单
//		if(id!=null){
//			// 加载指定角色的权限
//			Privilege privilege = new Privilege();
//			privilege.setRid(id);
//			List<Privilege> rolePs = privilegeService.selectList(privilege);
//			
//			// 拿角色拥有的菜单和全部的菜单做比对，进行勾选
//			for (int i = 0; i < rolePs.size(); i++) {
//				Privilege p = rolePs.get(i);
//				eeee(p, secures);
//			}
//		}
//		return writeMenus(secures);
//	}

	@Override
	public Services<Secure> getService() {
		return null;
	}
	
	/**
	 * 角色权限和资源菜单进行对比，使checkbox选中
	 * @param p
	 * @param menus
	 */
	private void eeee(Privilege p,List<MenuItem> menus){
		for (int j = 0; j < menus.size(); j++) {
			MenuItem menu = menus.get(j);
			if (p.getMid().equals(menu.getId())) {
				menu.setChecked(true);
				return;
			}else{
				if(menu.getChildren()!=null && menu.getChildren().size()>0)
					eeee(p, menu.getChildren());
			}
		}
	}
}
