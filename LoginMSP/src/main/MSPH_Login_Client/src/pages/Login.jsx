import React, { Component } from 'react';
import '../css/Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import axios from 'axios';
import md5 from 'md5';
import Cookies from 'universal-cookie';
import {Form, Button, Container} from 'react-bootstrap';
import Image from 'react-bootstrap/Image'
import logo from "../components/images/MSPH_LOGO.png"
const requestURL="http://localhost:3001/usuarios";
const cookies = new Cookies();
export default class Login extends Component {
    state={
        form:{
            username: '',
            password: ''
        }
    }

    handleChange=async e=>{
        await this.setState({
            form:{
                ...this.state.form,
                [e.target.name]: e.target.value
            }
        });
    }

    iniciarSesion=async()=>{
        await axios.get(requestURL, {params: {username: this.state.form.username, password: md5(this.state.form.password)}})
        .then(response=>{
            return response.data;
        })
        .then(response=>{
            if(response.length>0){
                var respuesta=response[0];
                cookies.set('id', respuesta.id, {path: "/"});
                cookies.set('apellido_paterno', respuesta.apellido_paterno, {path: "/"});
                cookies.set('apellido_materno', respuesta.apellido_materno, {path: "/"});
                cookies.set('nombre', respuesta.nombre, {path: "/"});
                cookies.set('username', respuesta.username, {path: "/"});
                alert(`Bienvenido ${respuesta.nombre} ${respuesta.apellido_paterno}`);
                window.location.href="./menu";
            }else{
                alert('El usuario o la contraseña no son correctos');
            }
        })
        .catch(error=>{
            console.log(error);
        })

    }

    componentDidMount() {
        if(cookies.get('username')){
            window.location.href="./menu";
        }
    }
    

    render() {
        return (
            <Container className ="w-auto text-center mx-auto p-3 mt-2 container">
                <Form className = "centered-element">
                    <Form.Group className="mb-3">
                    <Image src={logo}  fluid height={300} width={300} className='img-fluid hover-shadow'/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label>Nombre de usuario o correo electrónico: </Form.Label>
                        <Form.Control type="text" className="form-control" name="username" onChange={this.handleChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Contraseña: </Form.Label>
                        <Form.Control type="password" className="form-control" name="password" onChange={this.handleChange}/>
                    </Form.Group>
                    <div className = "text-center">
                        <Button className="btnSFR" type="button">
                            Ingresar
                        </Button>
                    </div>
                    <a href="/auth">Olvidó su contraseña?</a>
                </Form>
            </Container>
        );
    }
}
