package com.postales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.postales.entity.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Integer> {

	@Query("Select e from Envio e where "
			+ "( :p_usu is 0 or e.usuario.idUsuario = :p_usu )")
	public abstract List<Envio> listaEnvioUsu(
			                    @Param("p_usu") int idUsu);
	
	/*public List<Envio> findByIdLike(int idUsu);*/
	
	@Query("select e from Envio e")
	public List<Envio> listarEnvios();
	
	@Query("select e from Envio e where e.fechaCreacion = current_date")
	public List<Envio> listarEnviosPorDia();
	
	@Query("Select e from Envio e where "
			+ "e.usuario.idUsuario = ?1 and e.estado = 1")
	public List<Envio> listarEnviosEnviando(int idUsu);
	
	@Query("Select e from Envio e where "
			+ "e.usuario.idUsuario = ?1 and e.fechaCreacion = current_date")
	public List<Envio> listarEnviosPorIdYDia(int idUsu);
}
