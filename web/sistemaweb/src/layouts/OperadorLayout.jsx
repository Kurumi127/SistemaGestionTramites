import { NavLink, Navigate, Outlet, useNavigate } from "react-router-dom";
//import "./layout.css";

function OperadorLayout() {
  const navigate = useNavigate();
  const usuario = JSON.parse(localStorage.getItem("usuario"));

  if (!usuario) {
    return <Navigate to="/login" replace />;
  }

  if (usuario.tipoUsuario !== "OPERADOR") {
    return <Navigate to="/login" replace />;
  }

  const cerrarSesion = () => {
    localStorage.removeItem("usuario");
    navigate("/login");
  };

  return (
    <> 
        <div className="layout">
      <aside className="sidebar">
        <h2>Gestión de Trámites</h2>

        <p className="usuario">
          {usuario.nombre}
          <span>{usuario.tipoUsuario}</span>
        </p>

        <nav className="menu">
          <NavLink to="/operador/dashboard">Dashboard</NavLink>
          <NavLink to="/operador/areas">Mis áreas</NavLink>
          <NavLink to="/operador/servicios">Servicios del área</NavLink>
          <NavLink to="/operador/mis-servicios">Mis servicios</NavLink>
          <NavLink to="/operador/solicitudes">Mis solicitudes</NavLink>
        </nav>

        <button className="logout" onClick={cerrarSesion}>
          Cerrar sesión
        </button>
      </aside>

      <main className="contenido">
        <Outlet />
      </main>
    </div>
    
    </>

  );
}

export default OperadorLayout;