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
import { Container, Form, Button, Image, Row, Col } from 'react-bootstrap';
import PasswordRecoveryModal from './PasswordRecoveryModal';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import NavigationBar from '../components/NavigationBar';
import logo from "../components/images/MSPH_LOGO.png";
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
    this.cookies = new Cookies();
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
        this.cookies.set("username", response.data.username, { path: process.env.REACT_APP_AUTH, sameSite: 'Lax' });
        this.cookies.set("full_name", response.data.full_name, { path: process.env.REACT_APP_AUTH, sameSite: 'Lax' });
        this.cookies.set("roles", response.data.roles, { path: process.env.REACT_APP_AUTH, sameSite: 'Lax' });
        //cookies.set("user", response.data.user, { path: process.env.REACT_APP_AUTH, sameSite: 'Lax', secure: true });
        this.setState({
          username: '',
          pwd: '',
          disabled: true,
          showPassResetModal: false
        });
        toast.success("Bienvenido! :D", {
          position: toast.POSITION.TOP_RIGHT,
          pauseOnHover: true,
          theme: 'colored',
          autoClose: 5000
        });
        this.props.history.push('/menu');




      }).catch(error => {
        var msj = ""
        if (error.response) {
          switch (error.response.status) {
            case 401:
              msj = "Datos incorrectos, por favor vuelva a intentarlo.";
              break;
            case 500:
              msj = "El servidor ha encontrado un error desconocido.";
              break;
            default:
              msj = "El servidor ha encontrado un error desconocido.";
              break;
          }
        } else if (error.request) {
          //Server did not respond
          msj = "Hubo un error con la conexión al servidor."
        } else {
          //Something else went wrong
          msj = "Error desconocido."
        }
        toast.error(msj, {
          position: toast.POSITION.TOP_RIGHT,
          pauseOnHover: true,
          theme: 'colored',
          autoClose: 5000
        });
      })
    });
  }

  componentDidMount() {
    if (this.cookies.get('username', { path: process.env.REACT_APP_AUTH })
      && this.cookies.get('roles', { path: process.env.REACT_APP_AUTH })
      && this.cookies.get('full_name', { path: process.env.REACT_APP_AUTH })
    )
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
        <NavigationBar />
        <Container className="w-auto text-center mx-auto p-3 mt-2 container">
          <Row>
            <h1> Sistema de Identificación de la Municipalidad de San Pablo </h1>
          </Row>
          <Row className='mt-4'>
            <Col md={{ span: 2, offset: 5 }}>
              <Image
                src={logo}
                height={200}
                width={200}
              />
            </Col>
          </Row>
          <Row className='mt-4'>
            <Col md={{ span: 4, offset: 4 }}>
              <Form onSubmit={this.handleSubmit}>
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
            </Col>
          </Row>
        </Container>
        <PasswordRecoveryModal show={this.state.showPassResetModal} closeModal={this.hidePasswordReset} />
        <ToastContainer />
      </div>
    );
  }
}
