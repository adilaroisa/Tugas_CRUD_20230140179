package com.example.crudktp.controller;

import com.example.crudktp.model.dto.KtpDto;
import com.example.crudktp.model.WebResponse;
import com.example.crudktp.service.KtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ktp")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class KtpController {

    private final KtpService ktpService;

    // 1. POST /ktp: Tambah data baru
    @PostMapping
    public WebResponse<KtpDto> createKtp(@Valid @RequestBody KtpDto ktpDto) {
        KtpDto savedKtp = ktpService.createKtp(ktpDto);
        return new WebResponse<>(HttpStatus.CREATED.value(), "Data KTP berhasil ditambahkan", savedKtp);
    }

    // 2. GET /ktp: Ambil semua data
    @GetMapping
    public WebResponse<List<KtpDto>> getAllKtp() {
        List<KtpDto> ktpList = ktpService.getAllKtp();
        return new WebResponse<>(HttpStatus.OK.value(), "Berhasil mengambil semua data KTP", ktpList);
    }

    // 3. GET /ktp/{id}: Ambil data berdasarkan ID
    @GetMapping("/{id}")
    public WebResponse<KtpDto> getKtpById(@PathVariable Integer id) {
        KtpDto ktp = ktpService.getKtpById(id);
        return new WebResponse<>(HttpStatus.OK.value(), "Berhasil mengambil data KTP", ktp);
    }

    // 4. PUT /ktp/{id}: Update data
    @PutMapping("/{id}")
    public WebResponse<KtpDto> updateKtp(@PathVariable Integer id, @Valid @RequestBody KtpDto ktpDto) {
        KtpDto updatedKtp = ktpService.updateKtp(id, ktpDto);
        return new WebResponse<>(HttpStatus.OK.value(), "Data KTP berhasil diperbarui", updatedKtp);
    }

    // 5. DELETE /ktp/{id}: Hapus data
    @DeleteMapping("/{id}")
    public WebResponse<String> deleteKtp(@PathVariable Integer id) {
        ktpService.deleteKtp(id);
        return new WebResponse<>(HttpStatus.OK.value(), "Data KTP berhasil dihapus", null);
    }
}