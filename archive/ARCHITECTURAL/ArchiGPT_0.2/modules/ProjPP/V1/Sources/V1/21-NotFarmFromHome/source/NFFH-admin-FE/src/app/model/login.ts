export interface LoginRequest {
  email: string,
  password: string
}

export interface LoginResponse {
  token: string,
  success: boolean,
  id: string,
  username: string,
}
