import axios from "../../utils/axiosInstance.tsx";
import {FormEvent, useContext, useState} from "react";
import {AuthContext} from "../../contexts/authContext.tsx";
import {useNavigate} from "react-router-dom";
import {isAxiosError} from "axios";

export const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const authContext = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogin = async (e : FormEvent) => {
        e.preventDefault();
        try {
            await axios.post('/auth/login', { email, password });
            const response = await axios.post('/auth/verify');
            authContext?.setUser(response.data);
            navigate('/');
        } catch (error) {
            if (isAxiosError(error)) {
                console.error('Login failed:', error.response?.data || error.message);
                setError(error.response?.data || 'Invalid login or password');
            } else {
                console.error('Non-axios error:', error);
            }

        }
    };

    const handleOnRegister = async (e : React.MouseEvent) => {
        e.preventDefault();
        navigate('/register');
    };

    return (
        <div>
            <form onSubmit={handleLogin}>
                <h2>Login</h2>
                {error && <div>{error}</div>}

                <div>
                    <label>Email:</label>
                    <input
                        type="text"
                        required
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>

                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        required
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>

                <button type="submit" >Login</button>
            </form>

            <div >
                <h2>Or register</h2>
                <button onClick={handleOnRegister}>Register</button>
            </div>
        </div>
    );
}