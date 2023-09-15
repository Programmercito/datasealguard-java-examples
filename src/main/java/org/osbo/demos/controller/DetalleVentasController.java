package org.osbo.demos.controller;

import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osbo.core.datasealguard.exception.InvalidSignException;
import org.osbo.core.datasealguard.pojo.ValidateObject;
import org.osbo.core.datasealguard.validate.SignerDataValidate;
import org.osbo.core.datasealguard.type.TypeSign;
import org.osbo.demos.SessionSystem;
import org.osbo.demos.model.entities.DetalleVenta;
import org.osbo.demos.model.entities.User;
import org.osbo.demos.model.entities.Venta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author programmercito
 */
@RestController
@RequestMapping("detalleventas")
public class DetalleVentasController {

    @Value("${secret}")
    private String secret;

    @PostMapping
    public ResponseEntity<?> inserta(@RequestBody DetalleVenta detalleventa,
            HttpSession httpsession,
            @RequestHeader("validate") String valid) {

        User current = SessionSystem.getCurrent(httpsession);

        ValidateObject<Integer, Map<String, Integer>> validate = new ValidateObject<Integer, Map<String, Integer>>();
        Type type = new TypeToken<ValidateObject<Integer, Map<String, Integer>>>() {
        }.getType();

        SignerDataValidate<ValidateObject<Integer, Map<String, Integer>>> sign = new SignerDataValidate<ValidateObject<Integer, Map<String, Integer>>>();
        sign.setType(TypeSign.JWT)
                .setSecret(secret);

        try {
            validate = sign.extract(valid, type);
            if (current != null
                    && validate.getUser() == current.getUser()
                    && validate.getData().get("idventa") == detalleventa.getIdVenta()) {
                // aca hariamos la accion de persistir

                return ResponseEntity
                        .accepted()
                        .body(detalleventa);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("ud no tiene permisos sobre estos datos");
            }
        } catch (InvalidSignException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("ud no tiene permisos sobre estos datos");
        }

    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody DetalleVenta detalleventa,
            HttpSession httpsession,
            @RequestHeader("validate") String valid) {

        User current = SessionSystem.getCurrent(httpsession);

        ValidateObject<Long, Long> validate = new ValidateObject<Long, Long>();

        SignerDataValidate<ValidateObject<Long,Long>> sign = new SignerDataValidate<ValidateObject<Long,Long>>();
        sign.setType(TypeSign.JWT)
                .setSecret(secret);

        try {
            validate = sign.extract(valid);
            if (current != null
                    && validate.getUser() == current.getUser()
                    && validate.getData() == detalleventa.getIdVenta()) {
                // aca hariamos la accion de persistir

                return ResponseEntity
                        .accepted()
                        .body(detalleventa);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("ud no tiene permisos sobre estos datos");
            }
        } catch (InvalidSignException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("ud no tiene permisos sobre estos datos");
        }

    }
}
