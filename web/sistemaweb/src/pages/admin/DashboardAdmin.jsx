function DashboardAdmin() {
  const usuario = JSON.parse(localStorage.getItem("usuario"));

  return (
    <div>
      <h1>Dashboard administrador</h1>
      <p>Bienvenido, {usuario?.nombre}</p>
      <p>Tipo de usuario: {usuario?.tipoUsuario}</p>
    </div>
  );
}

export default DashboardAdmin;