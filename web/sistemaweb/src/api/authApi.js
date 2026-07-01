import api from "./axiosConfig";

export const login = async (credenciales) => {
  const response = await api.post("/auth/login", credenciales);
  return response.data;
};