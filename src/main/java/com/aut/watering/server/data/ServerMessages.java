package com.aut.watering.server.data;

public class ServerMessages {
	public static final String USERNAME_PWD_BLANK = "EL usuario o la contraseña estan en blanco";
	public static final String OK_MESSAGE = "Ok";
	
	public static final String NAME_NOT_VALID = "El nombre debe tener al menos 4 letras";
	public static final String EMAIL_NOT_VALID = "La dirección de email no es válida";
	public static final String PASSWORD_NOT_VALID = "La contraseña debe tener al menos 8 caracteres válidos: A-Z a-z 0-9 .";
	public static final String USER_ALREADY_CREATED = "El usuario {1} ya fue creado";
	public static final String USER_CREATED = "Usuario creado";
	public static final String USER_CREATION_ERROR = "Error creando usuario. Reintente nuevamente";
	public static final String GARDEN_NOT_FOUND = "Jardin inexistente";
	public static final String GARDEN_ERASED = "Jardin eliminado exitosamente";
	public static final String INTERNAL_ERROR = "Error interno, por favor reintente mas tarde";
	public static final String GARDEN_CREATED = "El jardin fue creado exitosamente";
	public static final String SPRINKLER_NOT_FOUND  = "Regador no encontrado";
	public static final String SPRINKLER_BAD_REQUEST = "Los umbrales de humedad deben pertenecer al rango 0-100% y el tiempo máximo de riego menor a {1}";
	public static final String SPRINKLER_CREATED = "Regador creado";
	public static final String SPRINKLER_DELETED = "Regador eliminado";
	public static final String GARDEN_MODIFIED = "Jardin modificado existosamente";
	public static final String INVALID_WORKING_HOURS = "El formato para informar el rango de horas de trabajo debe ser un JSON con siguiente sintaxis  {from:0, to:23}";
	public static final String INVALID_WORKING_DAYS = "El formato para informar los dias a trabajar debe ser una lista de numeros de dias. Ejemplo: [1,4,5,7] 1 = Domingo";
	public static final String UNAUTHORIZED_GARDEN_MODIFICATION = "EL jardin a modificar no pertenece al usuario actual";
}


