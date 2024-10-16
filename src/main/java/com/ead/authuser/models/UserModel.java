package com.ead.authuser.models;


import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_USER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends RepresentationModel<UserModel> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank
    private String username;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 150, name = "full_name")
    private String fullName;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false, name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(length = 20, name = "phone_number")
    private String phoneNumber;

    @Column(unique = true, length = 20)
    private String cpf;

    @Column(name = "url_image")
    private String imageUrl;

    @Column(nullable = false, name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(nullable = false, name = "last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;
}
