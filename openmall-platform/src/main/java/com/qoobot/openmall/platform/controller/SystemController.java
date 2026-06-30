package com.qoobot.openmall.platform.controller;

import com.qoobot.openmall.common.core.result.PageResult;
import com.qoobot.openmall.common.core.result.Result;
import com.qoobot.openmall.common.domain.entity.SysConfig;
import com.qoobot.openmall.common.domain.entity.SysMenu;
import com.qoobot.openmall.common.domain.entity.SysRole;
import com.qoobot.openmall.platform.service.SysConfigService;
import com.qoobot.openmall.platform.service.SysMenuService;
import com.qoobot.openmall.platform.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统管理控制器
 */
@Controller
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {

    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;
    private final SysConfigService sysConfigService;

    // ==================== 菜单管理 ====================

    @GetMapping("/menu")
    public String menuPage(Model model) {
        model.addAttribute("menuTree", sysMenuService.getMenuTree());
        return "system/menu";
    }

    @GetMapping("/api/menu/tree")
    @ResponseBody
    public Result<List<SysMenu>> getMenuTree() {
        return Result.success(sysMenuService.getMenuTree());
    }

    @PostMapping("/api/menu")
    @ResponseBody
    public Result<SysMenu> saveMenu(@RequestBody SysMenu menu) {
        return Result.success(sysMenuService.save(menu));
    }

    @PutMapping("/api/menu")
    @ResponseBody
    public Result<SysMenu> updateMenu(@RequestBody SysMenu menu) {
        return Result.success(sysMenuService.update(menu));
    }

    @DeleteMapping("/api/menu/{id}")
    @ResponseBody
    public Result<Void> deleteMenu(@PathVariable Long id) {
        sysMenuService.delete(id);
        return Result.success();
    }

    // ==================== 角色管理 ====================

    @GetMapping("/role")
    public String rolePage(Model model) {
        model.addAttribute("roles", sysRoleService.listAll());
        model.addAttribute("menuTree", sysMenuService.getMenuTree());
        return "system/role";
    }

    @GetMapping("/api/role")
    @ResponseBody
    public Result<List<SysRole>> listRoles() {
        return Result.success(sysRoleService.listAll());
    }

    @PostMapping("/api/role")
    @ResponseBody
    public Result<SysRole> saveRole(@RequestBody SysRole role) {
        return Result.success(sysRoleService.save(role));
    }

    @PutMapping("/api/role")
    @ResponseBody
    public Result<SysRole> updateRole(@RequestBody SysRole role) {
        return Result.success(sysRoleService.update(role));
    }

    @DeleteMapping("/api/role/{id}")
    @ResponseBody
    public Result<Void> deleteRole(@PathVariable Long id) {
        sysRoleService.delete(id);
        return Result.success();
    }

    @PostMapping("/api/role/{roleId}/menus")
    @ResponseBody
    public Result<Void> assignMenus(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        sysRoleService.assignMenus(roleId, menuIds);
        return Result.success();
    }

    // ==================== 参数配置 ====================

    @GetMapping("/config")
    public String configPage(@RequestParam(defaultValue = "1") int pageNum,
                             @RequestParam(defaultValue = "20") int pageSize,
                             Model model) {
        PageResult<SysConfig> page = sysConfigService.listPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "system/config";
    }

    @GetMapping("/api/config")
    @ResponseBody
    public Result<PageResult<SysConfig>> listConfig(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "20") int pageSize) {
        return Result.success(sysConfigService.listPage(pageNum, pageSize));
    }

    @PostMapping("/api/config")
    @ResponseBody
    public Result<SysConfig> saveConfig(@RequestBody SysConfig config) {
        return Result.success(sysConfigService.save(config));
    }

    @PutMapping("/api/config")
    @ResponseBody
    public Result<SysConfig> updateConfig(@RequestBody SysConfig config) {
        return Result.success(sysConfigService.update(config));
    }

    @DeleteMapping("/api/config/{id}")
    @ResponseBody
    public Result<Void> deleteConfig(@PathVariable Long id) {
        sysConfigService.delete(id);
        return Result.success();
    }
}
