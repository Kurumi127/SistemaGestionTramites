import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../../api/authApi";

function Login() {
  const navigate = useNavigate();

  const [correo, setCorreo] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState("");
  const [cargando, setCargando] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setCargando(true);

    try {
      const usuario = await login({
        correo,
        contrasena,
      });

      localStorage.setItem("usuario", JSON.stringify(usuario));

      if (usuario.tipoUsuario === "ADMINISTRADOR") {
        navigate("/admin/dashboard");
      } else if (usuario.tipoUsuario === "OPERADOR") {
        navigate("/operador/dashboard");
      } else {
        setError("Tipo de usuario no reconocido");
      }
    } catch (error) {
      const mensaje =
        error.response?.data?.mensaje || "No se pudo iniciar sesión";

      setError(mensaje);
    } finally {
      setCargando(false);
    }
  };

  return (
    <div>
      <h1>Iniciar sesión</h1>

      <form onSubmit={handleSubmit}>
        <div>
          <label>Correo</label>
          <input
            type="email"
            value={correo}
            onChange={(e) => setCorreo(e.target.value)}
            placeholder="correo@ejemplo.com"
            required
          />
        </div>

        <div>
          <label>Contraseña</label>
          <input
            type="password"
            value={contrasena}
            onChange={(e) => setContrasena(e.target.value)}
            placeholder="Contraseña"
            required
          />
        </div>

        {error && <p>{error}</p>}

        <button type="submit" disabled={cargando}>
          {cargando ? "Ingresando..." : "Ingresar"}
        </button>
      </form>
    </div>
  );
}

export default Login;