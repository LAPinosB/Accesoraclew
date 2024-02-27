package com;

public class UsaAccesoOracle {

	public static void main(String[] args) {
		// TODO Auto-generated method stubç
		AccesoOracle a = new AccesoOracle();
		a.abrirConexion();
		a.mostrarContactos();
		//ya creados los tipos en Oracle
        // a.crearTipoPersona();
        // a.crearTipoEstudiante();
		//Las tablas de igual manera ya estan creadas
        // a.crearTablaMisAlumnos();
		// a.crearTablaAdmitidos();
        a.insertarEstudiante("Luis", "123456789");
        a.insertarEstudiante("Jose", "987654321");
        a.eliminarAlumno("Jose");
        String telefonoEncontrado = a.buscarTelefonoAlumno("Luis");
        if(telefonoEncontrado!=null) {
        	System.out.println("El telefono es: "+telefonoEncontrado);
        }else {
        	System.out.println("No existe telefono en el Alumno");
        }
        a.mostrarInformacionAlumnos();
        a.insertarAdmitido("12/09/2023", "01A", "Pepe López", "967111111");
        a.mostrarInformacionAdmitidos();
		a.cerrarConexion();
	}

}
