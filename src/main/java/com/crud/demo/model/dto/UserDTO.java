package com.crud.demo.model.dto;

import com.crud.demo.model.entity.Arquivo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private boolean status;

    private List<Arquivo> files = new ArrayList<>();


    public void setFiles(List<MultipartFile> arquivos) throws IOException {
        for (MultipartFile arquivo : arquivos){
            Arquivo a = new Arquivo();
            a.setDados(arquivo.getBytes());
            a.setNome(arquivo.getOriginalFilename());
            a.setTipo(arquivo.getContentType());
            this.files.add(a);
        }
    }
}
