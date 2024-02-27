package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccesoOracle {
	private Connection con;

	void abrirConexion() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SYSTEM", "1234");
			System.out.println("Conexion OK");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Metodo: abrirConexion "+e.getMessage());
		}
	}

	void cerrarConexion() {
		try {
			System.out.println("Conexion cerrada");
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Metodo: cerrarConexion "+e.getMessage());
		}
	}

	void mostrarContactos() {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT c.nombre, c.telefono FROM contactos c");
			System.out.println("INFORMACION CONTACTOS --------");
			while (rs.next()) {
				System.out.printf("\nNOMBRE: %s \nTELEFONO:%s", rs.getString(1), rs.getString(2));
			}
			System.out.println("\n---------------");
			rs.close();
			st.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Metodo: mostrarContactos "+e.getMessage());
		}
	}

	/** Aqui REPLICAREMOS LO QUE HICIMOS MANUALMENTE EN ORACLESQLDEVELEOPER */
	public void crearTipoPersona() {
		try {
			Statement st = con.createStatement();
			String crearTabla = "CREATE TYPE PERSONA AS OBJECT (" + "nombre VARCHAR2(50)," + "telefono VARCHAR2(50))";
			st.executeUpdate(crearTabla);
		} catch (Exception e) {
			System.out.println("Metodo: crearTipoPersona "+e.getMessage());
		}
	}

	/**
	 * Crea una tabla misAlumnos de tipo estudiante (de la forma en que se creó la
	 * tabla contactos, pero usando el tipo ESTUDIANTE)
	 */
	public void crearTipoEstudiante() {
		try {
			Statement st = con.createStatement();
			String crearTabla = "CREATE TYPE ESTUDIANTE AS OBJECT (" + "nombre VARCHAR2(50),"
					+ "telefono VARCHAR2(50))";
			st.executeUpdate(crearTabla);
		} catch (Exception e) {
			System.out.println("Metodo: crearTipoEstudiante "+e.getMessage());
		}
	}

	public void crearTablaMisAlumnos() {
		try {
			Statement st = con.createStatement();
			String crearTabla = "CREATE TABLE MISALUMNOS OF ESTUDIANTE";
			st.executeUpdate(crearTabla);
		} catch (Exception e) {
			System.out.println("Metodo: crearTablaMisAlumnos "+e.getMessage());
		}
	}

	/* Insertar un estudiante en la tabla MISALUMNOS */
	public void insertarEstudiante(String nombre, String telefono) {
		try {
			String insertSQL = "INSERT INTO MISALUMNOS VALUES('" + nombre + "','" + telefono + "')";
			Statement st = con.createStatement();
			st.executeUpdate(insertSQL);
			System.out.println("Datos insertados");
		} catch (Exception e) {
			System.out.println("Metodo: insertarEstudiante "+e.getMessage());
		}
	}

	/**
	 * Borrar un alumno sabiendo su nombre. Suponemos que no hay dos alumnos con el
	 * mismo nombre
	 */
	public void eliminarAlumno(String nombre) {
		try {
			String deleteSQL = "DELETE FROM MISALUMNOS C WHERE C.nombre = '" + nombre + "'";
			Statement st = con.createStatement();
			st.executeUpdate(deleteSQL);
			System.out.println("Datos eliminados");
		} catch (Exception e) {
			System.out.println("Metodo: eliminarAlumno "+e.getMessage());
		}
	}

	/* Buscar el teléfono de un alumno sabiendo el nombre */
	public String buscarTelefonoAlumno(String nombre) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT telefono FROM MISALUMNOS WHERE nombre = '" + nombre + "'");
			if (rs.next()) {
				String telefono = rs.getString("telefono");
				rs.close();
				st.close();
				return telefono;
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println("Metodo: buscarTelefonoAlumno "+e.getMessage());
		}
		return null;
	}

	/**
	 * Mostrar un listado con toda la información de todos los alumnos (se puede
	 * hacer con un select similar a los que hay en la presentación powerpoint, o
	 * usando STRUCT)
	 */
	public void mostrarInformacionAlumnos() {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM MISALUMNOS");
			System.out.println("LISTADO DE ALUMNOS --------");
			while (rs.next()) {
				System.out.printf("\nNOMBRE: %s \nTELEFONO:%s", rs.getString("nombre"), rs.getString("telefono"));
			}
			System.out.println("\n---------------");
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println("Metodo: mostrarInformacionAlumnos "+e.getMessage());
		}
	}

	/**
	 * Mostrar toda la información de la TABLA ADMITIDOS (se puede hacer con un
	 * select similar al que hay en la presentación powerpoint, o usando STRUCT).
	 */
	public void crearTablaAdmitidos() {
		try {
			Statement st = con.createStatement();
			String crearTabla = "CREATE TABLE ADMITIDOS (dia DATE, matriculado ESTUDIANTE)";
			st.executeUpdate(crearTabla);
			System.out.println("Tabla ADMITIDOS creada correctamente");
		} catch (Exception e) {
			System.out.println("Metodo: crearTablaAdmitidos "+e.getMessage());
		}
	}

	// Insertaremos informacion en Admitidos cuando sea necesario: como hicimos
	// anteriormente con
	public void insertarAdmitido(String fecha, String idEstudiante, String nombre, String telefono) {
		try {
			String insertSQL = "INSERT INTO ADMITIDOS VALUES(TO_DATE('" + fecha + "', 'DD/MM/YYYY'), " + "estudiante('"
					+ idEstudiante + "', persona('" + nombre + "','" + telefono + "')))";
			Statement st = con.createStatement();
			st.executeUpdate(insertSQL);
			System.out.println("Admitido insertado");
			// Aquí puedes agregar la lógica para no insertar en contactos si es necesario
		} catch (Exception e) {
			System.out.println("Metodo: insertarAdmitido "+e.getMessage());
		}
	}

	public void mostrarInformacionAdmitidos() {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ADMITIDOS");
			System.out.println("LISTADO DE ADMITIDOS --------");
			while (rs.next()) {
				System.out.printf("\nNOMBRE: %s \nTELEFONO:%s", rs.getString("nombre"), rs.getString("telefono"));
				// Aquí puedes agregar más campos si existen en la tabla ADMITIDOS
			}
			System.out.println("\n---------------");
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println("Metodo: mostrarInformacionAdmitidos "+e.getMessage());
		}
	}

}
