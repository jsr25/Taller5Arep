package co.edu.escuelaing.arep.sparkserver.LogService.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GeneradorFecha {
    private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss ";

    public static String fecha(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
        ZoneId bogotaZoneId = ZoneId.of("America/Bogota");
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime americaZonedDateTime = now.atZone(bogotaZoneId);
        String fecha = format.format(americaZonedDateTime);
        return fecha;
    }
}
