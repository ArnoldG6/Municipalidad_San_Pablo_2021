import React, { Component } from 'react';
import './Plan.css'
import { Row, Card, Nav } from "react-bootstrap";
import CommentSideBar from './Components/CommentSideBar';
import TopButtons from './Components/TopButtons';
import axios from 'axios';


class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: "",
            authorName: "",
            name: "",
            description: "",
            entryDate: "",
            status: "",
            type: ""
        };
    }

    componentDidMount() {
        let query = new URLSearchParams(this.props.location.search);

        let options = {
            url: "http://localhost:8080/SFR/API/RetrievePlan",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'planID': query.get('id')
            }
        }
        axios(options)
            .then(response => {
                let plan = response.data;
                this.setState({
                    id: plan.id,
                    authorName: plan.authorName,
                    name: plan.name,
                    description: plan.description,
                    entryDate: plan.entryDate,
                    status: plan.status,
                    type: plan.type
                });
            }).catch(error => {
                this.props.history.push('/planes');
            });
    }

    render() {
        return (
            <div className="Plan-Container">
                {/* Comentarios del Plan */}
                <CommentSideBar />

                {/* Contenedor para el resto de la pagina */}
                <div className="container-fluid Data-Container">

                    {/* Botones de uso en el Plan */}
                    <Row>
                        <TopButtons status={this.state.status}/>
                    </Row>

                    {/* Datos del Plan */}
                    <Row className="mt-4">
                        <h1>{this.state.name}</h1>
                        <h2>{this.state.type}-{this.state.id}</h2>
                        <h4>{this.state.entryDate}</h4>
                        <h4>{this.state.authorName}</h4>
                        <p>{this.state.description}</p>
                    </Row>

                    {/* Listas de Datos del Plan */}
                    <Card>
                        <Card.Header>
                            <Nav variant="tabs" defaultActiveKey="#first">
                                {/*para que se vea la tab seleccionada ponerle como atributo active al nav.link*/}
                                <Nav.Item>
                                    <Nav.Link href="">Riesgos</Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link href="">Incidencias</Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                    <Nav.Link href="">
                                        Involucrados
                                    </Nav.Link>
                                </Nav.Item>
                            </Nav>
                        </Card.Header>
                        <Card.Body>

                        </Card.Body>
                    </Card>




                </div>
            </div>
        );
    }
}
export default Plan;