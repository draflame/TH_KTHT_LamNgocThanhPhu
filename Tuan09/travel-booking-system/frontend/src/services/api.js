import axios from "axios";

const API_URL =
  (import.meta.env && import.meta.env.VITE_ORCHESTRATOR_URL) || "http://localhost:8080";

// ✅ Tất cả requests đều qua Orchestrator
const apiClient = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Thêm interceptor để tự động lấy data từ response
apiClient.interceptors.response.use(
  (response) => response.data,
  (error) => {
    if (error.response && error.response.data) {
      return Promise.reject(error.response.data);
    }
    return Promise.reject(error);
  }
);

export const orchestratorAPI = {
  // Đăng nhập (qua Orchestrator -> User Service)
  login: (username, password) =>
    apiClient.post("/login", { username, password }),

  // Lấy danh sách tour (qua Orchestrator -> Tour Service)
  getTours: () => apiClient.get("/tours"),

  // Lấy chi tiết tour (qua Orchestrator -> Tour Service)
  getTourById: (tourId) => apiClient.get(`/tours/${tourId}`),

  // Đặt tour - FLOW CHÍNH (qua Orchestrator)
  bookTour: (bookingData) => apiClient.post("/book-tour", bookingData),
};

export default apiClient;
