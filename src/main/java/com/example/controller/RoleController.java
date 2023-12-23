package com.example.controller;

import com.example.dto.RoleDto;
import com.example.entity.Role;
import com.example.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
//@PreAuthorize("hasAuthority('admin')")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.getById(id));
    }


    @PostMapping("/")
    public ResponseEntity<Role> save(@Valid @RequestBody RoleDto roleDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService.save(roleDto));
    }
}
