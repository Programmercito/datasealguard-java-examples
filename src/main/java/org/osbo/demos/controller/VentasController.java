package org.osbo.demos.controller;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.osbo.core.datasealguard.pojo.ValidateObject;
import org.osbo.core.datasealguard.validate.SignerDataValidate;
import org.osbo.core.datasealguard.type.TypeSign;
import org.osbo.demos.SessionSystem;
import org.osbo.demos.model.entities.User;
import org.osbo.demos.model.entities.Venta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author programmercito
 */
@RestController
@RequestMapping("venta")
public class VentasController {

    @Value("${secret}")
    private String secret;

    @PostMapping
    public ResponseEntity<?> inserta(@RequestBody Venta venta, HttpSession httpsession) {
        User current = SessionSystem.getCurrent(httpsession);
        if (venta.getIdTienda() == current.getIdTienda() && current != null) {
            // aca hariamos la accion de persistir

            venta.setIdUsuario(current.getUser());

            // aca vamos a generar el token antes de responder 
            // depende como lo usamos puede estar en varios lugares
            ValidateObject<Integer, Map<String, Integer>> validate = new ValidateObject<Integer, Map<String, Integer>>();
            validate.setUser(current.getUser());
            validate.setData(new HashMap<String, Integer>());
            validate.getData().put("idventa", venta.getId());
            validate.getData().put("idtienda", venta.getIdTienda());
            SignerDataValidate sign = new SignerDataValidate();
            sign.setType(TypeSign.JWT)
                    .setTimeexpire(400)
                    .setSecret(secret);
            String token = sign.forSend(validate);

            // lo agregamos a la respuesta
            return ResponseEntity
                    .accepted()
                    .header("validation", token)
                    .body(venta);

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("ud no tiene permisos");
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Venta venta, HttpSession httpsession) {
        User current = SessionSystem.getCurrent(httpsession);
        if (venta.getIdTienda() == current.getIdTienda() && current != null) {
            // aca hariamos la accion de persistir

            venta.setIdUsuario(current.getUser());

            // aca vamos a generar el token antes de responder 
            // depende como lo usamos puede estar en varios lugares
            ValidateObject<Long, Long> validate = new ValidateObject<Long, Long>();
            validate.setUser(Long.valueOf(current.getUser()));
            validate.setData(Long.valueOf(venta.getId()));
           
            SignerDataValidate sign = new SignerDataValidate();
            sign.setType(TypeSign.JWT)
                    .setTimeexpire(400)
                    .setSecret(secret);
            String token = sign.forSend(validate);

            // lo agregamos a la respuesta
            return ResponseEntity
                    .accepted()
                    .header("validation", token)
                    .body(venta);

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("ud no tiene permisos");
        }
    }
}
