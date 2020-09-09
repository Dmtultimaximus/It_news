package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List getAllUsers(){
        return this.adminService.getUsers();
    }

    @GetMapping("/roles")
    public List getRoles(){
        return this.adminService.getRoles();
    }

    @DeleteMapping()
    public boolean deleteRole(@RequestParam Long userId, Long roleId){
        return this.adminService.delete(userId, roleId);
    }

    @GetMapping()
    public boolean addRole(@RequestParam Long userId, Long roleId){
        return this.adminService.add(userId, roleId);
    }
}
