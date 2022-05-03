/*
Authentication Module for SFR project.
@author Arnoldo J. González Quesada.
Github user: "ArnoldG6".
Contact me via: "arnoldgq612@gmail.com".
*/
import React from 'react';
import { sha256 } from 'js-sha256';
import '../css/Login.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import Cookies from 'universal-cookie';
import { Container, Form, Image, Button } from 'react-bootstrap';
import logo from "../components/images/MSPH_LOGO.png";
import PasswordRecoveryModal from './PasswordRecoveryModal';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import NavigationBar from '../components/NavigationBar';
const cookies = new Cookies();

export default class Login extends React.Component {
  /*
  Login class controls the request-response communication
  sent and received by the client in order to get authorization to access the other modules.
  */
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      pwd: '',
      disabled: true,
      showPassResetModal: false
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
    this.showPasswordReset = this.showPasswordReset.bind(this);
    this.hidePasswordReset = this.hidePasswordReset.bind(this);
  }

  handleSubmit(e) {
    e.preventDefault();
    this.setState({
      username: e.target.username.value,
      pwd: sha256(e.target.pwd.value),
      disabled: false
    }, () => {
      var options = {
        url: process.env.REACT_APP_AUTH_API_PATH + "/Auth",
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
        if (response.data.authStatus) {
          cookies.set("username", response.data.username, { path: process.env.REACT_APP_AUTH, sameSite: 'Lax', secure: true });
          cookies.set("full_name", response.data.full_name, { path: process.env.REACT_APP_AUTH, sameSite: 'Lax', secure: true });
          cookies.set("roles", response.data.roles, { path: process.env.REACT_APP_AUTH, sameSite: 'Lax', secure: true });
          this.setState({
            username: '',
            pwd: '',
            disabled: true,
            showPassResetModal: false
          });
          this.props.history.push('/menu');
        } else
          toast.error("Usuario o contraseña inválidos.", {
            position: toast.POSITION.TOP_RIGHT,
            pauseOnHover: true,
            theme: 'colored',
            autoClose: 5000
          });
      })
    });
  }

  componentDidMount() {
    if (cookies.get('username', { path: process.env.REACT_APP_AUTH })
      && cookies.get('roles', { path: process.env.REACT_APP_AUTH })
      && cookies.get('full_name', { path: process.env.REACT_APP_AUTH }))
      this.props.history.push('/menu');
  }
  async handleInputChange(e) {
    this.setState({ [e.target.name]: e.target.value }, () => {
      if (this.state.username.length === 0 || this.state.pwd.length === 0)
        this.setState({ disabled: true })
      else
        this.setState({ disabled: false })
    });
  }

  showPasswordReset = () => {
    this.setState({ showPassResetModal: true });
  }

  hidePasswordReset = () => {
    this.setState({ showPassResetModal: false });
  }

  render() {
    return (
      <div>
        <NavigationBar/>
        <Container className="w-auto text-center mx-auto p-3 mt-2 container">
          <Form className="centered-element" onSubmit={this.handleSubmit}>
            <Form.Group className="mb-3">
              <Image src={logo} height={300} width={300} className=' hover-shadow' onClick={() => { console.log(cookies) }} />
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
            <Button variant="link" onClick={this.showPasswordReset}>¿Olvidó su contraseña?</Button>
          </Form>
        </Container>
        <PasswordRecoveryModal show={this.state.showPassResetModal} closeModal={this.hidePasswordReset} />
        <ToastContainer />
      </div>
    );
  }
}
