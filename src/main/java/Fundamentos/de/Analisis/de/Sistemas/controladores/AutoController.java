
package Fundamentos.de.Analisis.de.Sistemas.controladores;

import Fundamentos.de.Analisis.de.Sistemas.modelos.Auto;
import Fundamentos.de.Analisis.de.Sistemas.modelos.Modelo;
import Fundamentos.de.Analisis.de.Sistemas.servicios.AutoServicio;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/autos")
public class AutoController {
    
    @Autowired
    private AutoServicio autoServicio;
    
    @GetMapping("/nuevo")
    public String mostrarFormularioAuto(Model model){
        model.addAttribute("auto",new Auto());
        return "formulario-cargar-vehiculo";
    }
    

    
    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearAuto(
        @RequestParam("modelo") Modelo modelo,
        @RequestParam("version") String version,
        @RequestParam("proveedor") String proveedor,
        @RequestParam("matricula") String matricula,
        @RequestParam("precio") float precio,
        @RequestParam("color") String color,
        @RequestParam("anio") int anio,
        @RequestParam("serie") String serie,
        @RequestParam(value = "imagen", required = false) MultipartFile imagen
    ) {
        System.out.println("ENTRO AL BACK");
        
        System.out.println(modelo.toString());
        
        try {
            Auto auto = new Auto();
            auto.setModelo(modelo);
            auto.setVersion(version);
            auto.setProveedor(proveedor);
            auto.setMatricula(matricula);
            auto.setPrecio(precio);
            auto.setColor(color);
            auto.setAnioFabricacion(anio);
            auto.setStock(10);
            auto.setSerie(serie);
            if (imagen != null && !imagen.isEmpty()) {
                auto.setImagen(imagen.getBytes()); 
            }

            autoServicio.guardarAuto(auto);
            return ResponseEntity.ok(auto); 
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la imagen");
        }
    }
    
    
    
    @GetMapping("/listar")
    @ResponseBody //Lo convierte en un JSON
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Auto> listarAutos(){
        return autoServicio.obtenerTodos();
    }
}
