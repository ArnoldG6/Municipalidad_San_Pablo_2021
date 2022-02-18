import React from 'react';
import { sha256 } from 'js-sha256';
import '../css/Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import Cookies from 'universal-cookie';
import { Container, Form, Image, Button } from 'react-bootstrap'
import logo from "../components/images/MSPH_LOGO.png"
const requestURL = "http://localhost:8080/auth/API/Auth";
const cookies = new Cookies();
export default class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      pwd: '',
      disabled: true
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
  }
  async handleSubmit(e) {
    e.preventDefault();
    this.setState({
        username: e.target.username.value,
        pwd: sha256(e.target.pwd.value),
        disabled: false
      }, () => {
      var options = {
          url: requestURL,
          method: 'POST',
          header: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          data: {
            'username': this.state.username,
            'pwd': this.state.pwd
          }
        }
        axios(options).then(response => {
          if (response.length > 0)
            if (response.data.authStatus === false)
              alert('El usuario o la contraseña no son correctos xd');
            else
              window.location.href = "/menu";
          else
            alert('El usuario o la contraseña no son correctos');
        }).catch(ex => {
          console.log(ex);
          alert(ex);
        });
      });

  }
  async componentDidMount() {
    if (cookies.get('username' && cookies.get('roles') && cookies.get('token'))) {
      window.location.href = "/menu";
      alert(cookies);
    }
  }
  async handleInputChange(e) {
    this.setState({ [e.target.name]: e.target.value }, () => {
      if (this.state.username.length === 0 || this.state.pwd.length === 0)
        this.setState({ disabled: true })
      else
        this.setState({ disabled: false })
      /*console.log(`username: {${this.state.username}}`)
      console.log(`pwd: {${this.state.pwd}}`)*/
    });
  }
  render() {
    return (
      <Container className="w-auto text-center mx-auto p-3 mt-2 container">
        <Form className="centered-element" onSubmit={this.handleSubmit}>
          <Form.Group className="mb-3">
            <Image src={logo} fluid height={300} width={300} className='img-fluid hover-shadow' />
          </Form.Group>
          <Form.Group className="mb-3" >
            <Form.Label>Nombre de usuario o correo electrónico: </Form.Label>
            <Form.Control autoFocus type="text" name="username" onChange={this.handleInputChange} />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Contraseña: </Form.Label>
            <Form.Control type="password" name="pwd" onChange={this.handleInputChange} />
          </Form.Group>
          <div className="text-center">
            <Button className="btnSFR" type="submit" disabled={this.state.disabled}>
              Ingresar
            </Button>
          </div>
          {/*<a href="/auth">Olvidó su contraseña?</a>*/}
        </Form>
      </Container>
    );
  }
}