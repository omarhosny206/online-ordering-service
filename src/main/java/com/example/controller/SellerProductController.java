package com.example.controller;

import com.example.dto.SellerProductDto;
import com.example.dto.UpdateSellerProductDto;
import com.example.entity.SellerProduct;
import com.example.response.MessageResponse;
import com.example.service.SellerProductService;
import com.example.util.AuthenticationUser;
import com.example.util.SellerProductId;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers-products")
@PreAuthorize("hasAnyAuthority('seller')")
public class SellerProductController {
    private final SellerProductService sellerProductService;

    @Autowired
    public SellerProductController(SellerProductService sellerProductService) {
        this.sellerProductService = sellerProductService;
    }

    @GetMapping("/")
    public ResponseEntity<List<SellerProduct>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sellerProductService.getAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<SellerProduct> getById(Authentication authentication, @PathVariable long productId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sellerProductService.getById(new SellerProductId(AuthenticationUser.get(authentication).getId(), productId)));
    }

    @PostMapping("/")
    public ResponseEntity<SellerProduct> save(Authentication authentication, @Valid @RequestBody SellerProductDto sellerProductDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sellerProductService.save(AuthenticationUser.get(authentication), sellerProductDto));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<SellerProduct> update(Authentication authentication, @PathVariable int productId, @Valid @RequestBody UpdateSellerProductDto updateSellerProductDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sellerProductService.update(AuthenticationUser.get(authentication), productId, updateSellerProductDto));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<MessageResponse> delete(Authentication authentication, @PathVariable int productId) {
        sellerProductService.deleteByProductId(AuthenticationUser.get(authentication), productId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Deleted successfully"));
    }
}
