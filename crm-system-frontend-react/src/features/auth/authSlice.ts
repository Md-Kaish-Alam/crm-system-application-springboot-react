import axios from "axios";
import { createSlice, createAsyncThunk, PayloadAction } from "@reduxjs/toolkit";

import { User, AuthResponse } from "@/types/types";
import axiosInstance from "@/axiosInstance";

interface AuthState {
    user: User | null;
    token: string | null;
    loading: boolean;
    error: string | null;
}

const initialState: AuthState = {
    user: null,
    token: null,
    loading: false,
    error: null,
};

export const signup = createAsyncThunk(
    "auth/signup",
    async (user: User, { rejectWithValue }) => {
        try {
            const response = await axiosInstance.post<AuthResponse>("/auth/signup", user);
            return response.data;
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                return rejectWithValue(error.response.data);
            } else {
                return rejectWithValue("An unknown error occurred.");
            }
        }
    }
);

export const signin = createAsyncThunk(
    "auth/signin",
    async (user: User, { rejectWithValue }) => {
        try {
            const response = await axiosInstance.post<AuthResponse>("/auth/signin", user);
            return response.data;
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                return rejectWithValue(error.response.data);
            } else {
                return rejectWithValue("An unknown error occurred.");
            }
        }
    }
);

export const logout = createAsyncThunk(
    'auth/logout',
    async (token: string, { rejectWithValue }) => {
        try {
            const response = await axiosInstance.post<AuthResponse>('/auth/logout', null, {
                headers: { Authorization: `Bearer ${token}` },
            });
            return response.data;
        } catch (error) {
            if (axios.isAxiosError(error) && error.response) {
                return rejectWithValue(error.response.data);
            } else {
                return rejectWithValue("An unknown error occurred.");
            }
        }
    }
);

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        setUser: (state, action: PayloadAction<User>) => {
            state.user = action.payload;
        },
        setToken: (state, action: PayloadAction<string | null>) => {
            state.token = action.payload;
        },
        setLoading: (state, action: PayloadAction<boolean>) => {
            state.loading = action.payload;
        },
        setError: (state, action: PayloadAction<string | null>) => {
            state.error = action.payload;
        },
        clearAuthState: (state) => {
            state.user = null;
            state.token = null;
            state.loading = false;
            state.error = null;
        },

    },
    extraReducers: (builder) => {
        builder
            .addCase(signup.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(signup.fulfilled, (state, { payload }) => {
                state.loading = false;
                state.token = payload.jwt;
            })
            .addCase(signup.rejected, (state, { payload }) => {
                state.loading = false;
                state.error = payload as string;
            })
            .addCase(signin.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(signin.fulfilled, (state, { payload }) => {
                state.loading = false;
                state.token = payload.jwt;
            })
            .addCase(signin.rejected, (state, { payload }) => {
                state.loading = false;
                state.error = payload as string;
            })
            .addCase(logout.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(logout.fulfilled, (state) => {
                state.loading = false;
                state.token = null;
                state.user = null;
            })
            .addCase(logout.rejected, (state, { payload }) => {
                state.loading = false;
                state.error = payload as string;
            });
    },
});

export const { setUser, setToken, setLoading, setError, clearAuthState } = authSlice.actions;
export default authSlice.reducer;
