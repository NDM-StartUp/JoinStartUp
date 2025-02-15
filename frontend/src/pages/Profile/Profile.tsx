import axiosInstance from "../../utils/axiosInstance.tsx";
import {useContext, useEffect, useState} from "react";
import axios from "../../utils/axiosInstance.tsx";
import {isAxiosError} from "axios";
import {AuthContext} from "../../contexts/authContext.tsx";
import {ProfileData} from "../../types";

export const Profile = () => {
    const authContext = useContext(AuthContext);
    const [isLoading, setIsLoading] = useState(true);
    const [profileData, setProfileData] = useState<ProfileData | null>();
    const handleLogout = async () => {
        try {
            await axiosInstance.post(`/auth/logout`);
            window.location.reload();
        } catch (err) {
            console.error("Error logout:", err);
        }

    }

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await axios.get(`/users/${authContext?.user?.id}/user-info`);
                setProfileData(response.data);
            } catch (error) {
                if (isAxiosError(error)) {
                    console.error('Verification failed:', error.response?.data || error.message);
                } else {
                    console.error('Non-axios error:', error);
                }
            } finally {
                setIsLoading(false);
            }
        };
        fetchUser();
    }, []);
    return (
        <>
            <h2>Profile</h2>

            {isLoading ? (
                <p>Loading...</p>
            ) : (
                profileData && (
                    <>
                        <h3>{profileData.firstName} {profileData.lastName}</h3>
                        <h3>{profileData.email}</h3>
                        <h3>{profileData.phone}</h3>
                        <h3>{profileData.description}</h3>
                        <button onClick={handleLogout}>Log out</button>
                    </>
                )
            )}
        </>
    )
}