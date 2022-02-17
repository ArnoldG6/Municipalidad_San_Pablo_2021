import React, { Component,useState } from 'react';
import '../css/Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import Cookies from 'universal-cookie';
import {Form, Button, Container} from 'react-bootstrap';
import Image from 'react-bootstrap/Image'
import logo from "../components/images/MSPH_LOGO.png"
const requestURL="http://localhost:8080/auth/API/Auth";
const cookies = new Cookies();
export default class Login extends Component {
    username = useState('');
    pwd = useState('');
    handleChange = (event) => {
        username = event.target.username;
        pwd = event.target.pwd;
    }
    handleSubmit = async(event) => {
        event.preventDefault();
        console.log({pwd});
        console.log({username});
        let options = {
            url: requestURL,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'username': {username},
                'pwd': {pwd},
            }
        }
        axios(options).then(response => {
            if(response.length>0){
                if(response.data.authStatus === false){
                    console.log("hola");
                    alert('El usuario o la contraseña no son correctos');
                }else{
                    cookies.set('username', response.data.username, {path: "/"});
                    cookies.set('roles', response.data.roles, {path: "/"});
                    cookies.set('token', response.data.token,{path: "/"});
                    window.location.href="./menu";
                }            
            }else
                alert('El usuario o la contraseña no son correctos');
        }).catch(ex => {
            console.log(ex);
            alert(ex);
        });
    }


    componentDidMount() {
        if(cookies.get('username' && cookies.get('roles') && cookies.get('token'))){
            window.location.href="/menu";
            alert(cookies);
        }
    }
    

    render() {
        return (
            <Container className ="w-auto text-center mx-auto p-3 mt-2 container">
                <Form className = "centered-element">
                    <Form.Group className="mb-3">
                    <Image src={logo}  fluid height={300} width={300} className='img-fluid hover-shadow'/>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Nombre de usuario o correo electrónico: </Form.Label>
                        <Form.Control type="text" className="form-control" 
                        id="username" value={username || ""}  onChange={handleChange}/>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Contraseña: </Form.Label>
                        <Form.Control type="password" className="form-control" id="pwd"
                        value={pwd || ""}  onChange={handleChange}/>
                    </Form.Group>
                    <div className = "text-center">
                        <Button className="btnSFR" type="button" onClick={this.handleSubmit}>
                            Ingresar
                        </Button>
                    </div>
                    {/*<a href="/auth">Olvidó su contraseña?</a>*/}
                </Form>
            </Container>
        );
    }
}
