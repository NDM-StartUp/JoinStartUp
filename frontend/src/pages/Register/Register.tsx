import axios from '../../utils/axiosInstance.js';
import { useNavigate } from 'react-router-dom';
import { FormEvent, useState } from 'react';
import { isAxiosError } from 'axios';

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [phone, setPhone] = useState('');
    const [description, setDescription] = useState('');
    const [error, setError] = useState('');

    const navigate = useNavigate();

    const handleRegister = async (e: FormEvent) => {
        e.preventDefault();
        try {
            const payload = { email, password, firstName, lastName, phone, description };

            await axios.post('/auth/register', payload);

            alert('Registration successful. You can now log in.');
            navigate('/login');
        } catch (error) {
            if (isAxiosError(error)) {
                console.error('Registration failed:', error.response?.data || error.message);
                setError(error.response?.data || 'An unexpected error occurred');
            } else {
                console.error('Non-axios error:', error);
            }
        }
    };

    const handleOnLogin = async (e: React.MouseEvent) => {
        e.preventDefault();
        navigate('/login');
    };

    return (
        <div>
            <form onSubmit={handleRegister}>
                <h2>Register</h2>
                {error && <div>{error}</div>}

                <div>
                    <label>First Name:</label>
                    <input
                        type="text"
                        required
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                    />
                </div>

                <div>
                    <label>Last Name:</label>
                    <input
                        type="text"
                        required
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                    />
                </div>

                <div>
                    <label>Email:</label>
                    <input
                        type="email"
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

                <div>
                    <label>Phone:</label>
                    <input
                        type="text"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                    />
                </div>

                <div>
                    <label>Description:</label>
                    <textarea
                        required
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </div>

                <button type="submit">Register</button>
            </form>

            <div>
                <h2>Or login</h2>
                <button onClick={handleOnLogin}>Login</button>
            </div>
        </div>
    );
};

export default Register;
