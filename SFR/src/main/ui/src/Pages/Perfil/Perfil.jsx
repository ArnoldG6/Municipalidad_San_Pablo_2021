import React, { Component } from 'react';
import axios from 'axios';
import { Row, Card, Table, Container, Col } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import GenericModal from '../../SharedComponents/GenericModal/GenericModal';
import Cookies from 'universal-cookie';
const cookies = new Cookies();

export default class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            showEdit: false
        };
        //this.refreshPage = this.refreshPage.bind(this);
    }

    componentDidMount() {
        //Account check
        if (typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('token', { path: process.env.REACT_APP_AUTH }) === 'undefined' ||
            typeof cookies.get('full_name', { path: process.env.REACT_APP_AUTH }) === 'undefined') {
            document.location = process.env.REACT_APP_LOGOUT;
        }

        //this.refreshPage();
    }

    /*   refreshPage() {
           let query = new URLSearchParams(this.props.location.search);
   
           let options = {
               url: process.env.REACT_APP_SFR_API_URL + "/RiskServlet/Retrieve/Riesgo",
               method: "POST",
               header: {
                   'Accept': 'application/json',
                   'Content-Type': 'application/json'
               },
               data: {
                   'riskID': query.get('id')
               }
           }
           axios(options)
               .then(response => {
                   this.setState({
                       risk: response.data
                   }, () => {
                       if (this.state.risk === null || typeof this.state.risk === 'undefined') {
                           this.props.history.push('/riesgos');
                       } else {
                           this.retrieveTypes();
                       }
                   });
               })
               .catch(error => {
                   this.props.history.push('/riesgos');
               });

               
       }
    
   */

    openModalEdit = () => {
        this.setState({ showEdit: true });
    };

    closeModalEdit = () => {
        this.setState({ showEdit: false });
    };

    render() {
        return (
            <div className="Plan-Container">
                user = {this.state.user}
                {/* Mobile */}
                <Row className="mt-4">
                    {
                        (this.state.user === null || typeof this.state.user === 'undefined') ?
                            <h1>Cargando Datos</h1> :
                            //Hay que hacer la validacion bien, este solo fue un ejemplo
                            (this.state.user.username === this.props.username) ?
                                <div>
                                    <h1>Nombre: {this.props.user.full_name}</h1>
                                    <h1>Usuario: {this.props.user.username}</h1>
                                    <h1>Departamento: {this.props.user.department}</h1>
                                    <h1>Rol: {this.props.user.roles}</h1>
                                </div> :
                                <div>
                                    <h1>Nombre: {this.state.user.full_name}</h1>
                                    <h1>Usuario: {this.state.user.username}</h1>
                                    <h1>Departamento: {this.state.user.department}</h1>
                                    <h1>Rol: {this.state.user.roles}</h1>
                                </div>
                    }
                </Row>
                {/* Vista Desktop */}
                <div className="d-none d-lg-block">
                    (this.state.user === null || typeof this.state.user === 'undefined') ?
                    <h1>Cargando Datos</h1> :
                    //Hay que hacer la validacion bien, este solo fue un ejemplo
                    (this.state.user.username === this.props.username) ?
                    <div>
                        <h1>Nombre: {this.props.user.full_name}</h1>
                        <h1>Usuario: {this.props.user.username}</h1>
                        <h1>Departamento: {this.props.user.department}</h1>
                        <h1>Rol: {this.props.user.roles}</h1>
                    </div> :
                    <div>
                        <h1>Nombre: {this.state.user.full_name}</h1>
                        <h1>Usuario: {this.state.user.username}</h1>
                        <h1>Departamento: {this.state.user.department}</h1>
                        <h1>Rol: {this.state.user.roles}</h1>
                    </div>

                </div>
                
                <ToastContainer />
            </div>
        );
    }
}