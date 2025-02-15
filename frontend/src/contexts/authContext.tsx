import React, { createContext, useState, useEffect, ReactNode } from 'react';
import axios from "../utils/axiosInstance.tsx";
import {User} from "../types";
import {isAxiosError} from "axios";

interface AuthContextType {
    user: User | null;
    setUser: React.Dispatch<React.SetStateAction<User | null>>;
    isLoading : boolean
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<User | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await axios.post("/auth/verify");
                setUser(response.data);
            } catch (error) {
                if (isAxiosError(error)) {
                    console.error('Verification failed:', error.response?.data || error.message);
                } else {
                    console.error('Non-axios error:', error);
                }
                setUser(null);
            } finally {
                setIsLoading(false);
            }
        };
        fetchUser();
    }, []);

    return (
        <AuthContext.Provider value={{ user, setUser, isLoading }}>
            {children}
        </AuthContext.Provider>
    );
};