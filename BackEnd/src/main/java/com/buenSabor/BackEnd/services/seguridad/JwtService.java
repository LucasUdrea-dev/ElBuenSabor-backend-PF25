package com.buenSabor.BackEnd.services.seguridad;

import com.buenSabor.BackEnd.models.seguridad.UserAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-minutes}") //Valor seteado en application.properties
    private long EXPIRACTION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserAuthentication usuario, Map<String,Object> extraClaims) {

        Date fechaEmision = new Date(System.currentTimeMillis());
        Date fechaExpiracion = new Date(fechaEmision.getTime() + (EXPIRACTION_MINUTES * 60 * 1000));//convertir a ms

        extraClaims.put("id_user", usuario.getUsuario().getId());


        return Jwts.builder()
                .setClaims(extraClaims) //Map trae usuario y rol
                .setSubject(usuario.getUsername()) //usuario
                .setIssuedAt(fechaEmision) //fecha inicio token
                .setExpiration(fechaExpiracion) //fecha expiracion token
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Parametros del token, segun doc jwt.io https://www.jwt.io/
                .signWith(generateKey(), SignatureAlgorithm.HS256)//Firma backend
                .compact();
    }

    private Key generateKey(){

        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);

        //System.out.println("Clave desde Back para firma JWT: " + new String(secretAsBytes));

        return Keys.hmacShaKeyFor(secretAsBytes);
    }

    private Claims getClaims(String jwt) {
        //.parseClaimsJws(jwt) cuando estoy seguro que le mando la firma, sino usar.parseClaimsJwt(jwt), que tiene header y body
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(jwt).getBody();
    }

    public String extractUsername(String jwt) {
        return  getClaims(jwt).getSubject();
    }
}
