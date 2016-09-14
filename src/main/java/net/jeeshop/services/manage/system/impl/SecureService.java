package net.jeeshop.services.manage.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.Services;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.system.bean.Menu;
import net.jeeshop.core.system.bean.MenuItem;
import net.jeeshop.core.system.bean.MenuType;
import net.jeeshop.core.system.bean.Secure;
import net.jeeshop.core.system.bean.SecureItem;
import net.jeeshop.core.system.bean.User;

public class SecureService implements Services<Secure> {

	@Override
	public int insert(Secure e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Secure e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletes(String[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Secure e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Secure selectOne(Secure e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Secure selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(Secure e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Secure> selectList(Secure e) {
		// TODO Auto-generated method stub
		return null;
	}
	// 加载根节点
		public List<SecureItem> loadSecures(User u, String pid, String url) {
			Map<String, String> param = new HashMap<String, String>();
			if (u != null && u.getRid() != null) {
				param.put("rid", u.getRid());// 角色ID
			}
			param.put("pid", pid);// 菜单父ID
			List<Menu> menus = dao.selectList("menu.selectMenus", param);
			// 创建菜单集合
			List<MenuItem> root = new ArrayList<MenuItem>();
			// 循环添加菜单到菜单集合
//			for (Iterator<Menu> it = menus.iterator(); it.hasNext();) {
			for(int i=0;i<menus.size();i++){
//				Menu entry = it.next();
				Menu menu = menus.get(i);
				MenuItem item = new MenuItem(menu.getName(), null);
				item.setId(menu.getId());
				item.setPid(menu.getPid());
				item.setMenuType(menu);
				if (url != null) {
					item.setUrl(url);
				} else {
					item.setUrl(menu.getUrl());
				}
				root.add(item);
			}
			// 加载子菜单，注意 只加载type为模块级或页面级的
			for (int i = 0; i < root.size(); i++) {
				MenuItem ee = root.get(i);
				if(ee.getType()==null || ee.getType().toString().equals("") || ee.getType().equals(MenuType.button)){
					continue;
				}
				
				Menu mm = new Menu();
				mm.setPid(ee.getId());
				loadChildrenByPid(ee, mm, url, u);
			}

			return root;
		}

		/**
		 * 根据父ID加载子节点
		 * @param item
		 * @param menu
		 * @param url
		 * @param u
		 */
		private void loadChildrenByPid(MenuItem item, Menu menu, String url, User u) {
			Map<String, String> param = new HashMap<String, String>();

			if (u != null && u.getRid() != null)
				param.put("rid", u.getRid());
			param.put("pid", menu.getPid());
			// 加载菜单节点
			List<Menu> data = dao.selectList("menu.selectMenus", param);
			if (data == null || data.size() == 0) {
				return;
			}
			if (item.getChildren() == null)
				item.setChildren(new ArrayList<MenuItem>());
			// 创建菜单节点
			for (int i = 0; i < data.size(); i++) {
				Menu entry = data.get(i);
				MenuItem addItem = new MenuItem(entry.getName(), null);
				addItem.setId(entry.getId());
				addItem.setPid(entry.getPid());
				addItem.setMenuType(entry);
				if (url != null) {
					addItem.setUrl(url);
				} else {
					addItem.setUrl(entry.getUrl());
				}
				item.getChildren().add(addItem);
			}
			// 根据菜单节点进行递归加载
			for (int i = 0; i < item.getChildren().size(); i++) {
				Menu itemMenu = new Menu();

				itemMenu.setPid(item.getChildren().get(i).getId());
				loadChildrenByPid(item.getChildren().get(i), itemMenu, url, u);
			}
		}
}
