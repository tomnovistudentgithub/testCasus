import React, { useContext } from 'react';
import { useForm } from 'react-hook-form';
import AuthContext from '../../Context/AuthContext.jsx';

const LoginPage = () => {
    const { register, handleSubmit, formState: { errors } } = useForm();
    const { login, error } = useContext(AuthContext);

    const onSubmit = ({ email, password }) => {
        login(email, password);
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <label>
                Email:
                <input type="email" {...register('email', { required: 'Email is required' })} />
                {errors.email && <p>{errors.email.message}</p>}
            </label>
            <label>
                Password:
                <input type="password" {...register('password', { required: 'Password is required' })} />
                {errors.password && <p>{errors.password.message}</p>}
            </label>
            {error && <p>{error}</p>}
            <input type="submit" value="Login" />
        </form>
    );
};

export default LoginPage;