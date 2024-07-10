export interface User {
  email: string;
  password: string;
  fullName?: string;
  mobile?: string;
  role?: string;
}

export interface AuthResponse {
  jwt: string;
  message: string;
  status: boolean;
}

export interface SalesPipeline {
  id: string;
  customerId: string;
  stage: string;
  description: string;
}

export interface EmailLog {
  id: string;
  customerId: string;
  subject: string;
  content: string;
  sentAt: string;
}

export interface Task {
  id: string;
  customerId: string;
  title: string;
  description: string;
  dueDate: string;
  completed: boolean;
}

export interface Customer {
  id: string;
  name: string;
  email: string;
  mobile: string;
  address: string;
  salesPipelines: SalesPipeline[];
  emailLogs: EmailLog[];
  tasks: Task[];
}
