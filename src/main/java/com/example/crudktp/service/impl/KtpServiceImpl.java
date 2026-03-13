package com.example.crudktp.service.impl;

import com.example.crudktp.model.dto.KtpDto;
import com.example.crudktp.model.entity.Ktp;
import com.example.crudktp.mapper.KtpMapper;
import com.example.crudktp.repository.KtpRepository;
import com.example.crudktp.service.KtpService;
import com.example.crudktp.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KtpServiceImpl implements KtpService {

    private final KtpRepository ktpRepository;
    private final KtpMapper ktpMapper;
    private final ValidationUtil validationUtil; // <-- Mengambil fungsi util

    @Override
    public KtpDto createKtp(KtpDto ktpDto) {
        validationUtil.validate(ktpDto);

        // Validasi NIK kembar
        if (ktpRepository.existsByNomorKtp(ktpDto.getNomorKtp())) {
            throw new RuntimeException("Error: Nomor KTP sudah terdaftar!");
        }

        Ktp ktp = ktpMapper.toEntity(ktpDto);
        Ktp savedKtp = ktpRepository.save(ktp);
        return ktpMapper.toDto(savedKtp);
    }

    @Override
    public List<KtpDto> getAllKtp() {
        return ktpRepository.findAll().stream()
                .map(ktpMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public KtpDto getKtpById(Integer id) {
        Ktp ktp = ktpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Data KTP tidak ditemukan!"));
        return ktpMapper.toDto(ktp);
    }

    @Override
    public KtpDto updateKtp(Integer id, KtpDto ktpDto) {
        validationUtil.validate(ktpDto);

        Ktp existingKtp = ktpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Data KTP tidak ditemukan!"));

        if (!existingKtp.getNomorKtp().equals(ktpDto.getNomorKtp()) &&
                ktpRepository.existsByNomorKtp(ktpDto.getNomorKtp())) {
            throw new RuntimeException("Error: Nomor KTP baru sudah terdaftar!");
        }

        existingKtp.setNomorKtp(ktpDto.getNomorKtp());
        existingKtp.setNamaLengkap(ktpDto.getNamaLengkap());
        existingKtp.setAlamat(ktpDto.getAlamat());
        existingKtp.setTanggalLahir(ktpDto.getTanggalLahir());
        existingKtp.setJenisKelamin(ktpDto.getJenisKelamin());

        return ktpMapper.toDto(ktpRepository.save(existingKtp));
    }

    @Override
    public void deleteKtp(Integer id) {
        if (!ktpRepository.existsById(id)) {
            throw new RuntimeException("Error: Data KTP tidak ditemukan!");
        }
        ktpRepository.deleteById(id);
    }
}