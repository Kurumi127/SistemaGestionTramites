function DashboardOperador() {
  const usuario = JSON.parse(localStorage.getItem("usuario"));

  return (
    <div>
      <h1>Dashboard operador</h1>
      <p>Bienvenido, {usuario?.nombre}</p>
      <p>Tipo de usuario: {usuario?.tipoUsuario}</p>
    </div>
  );
}

export default DashboardOperador;