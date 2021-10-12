import React, { Component } from 'react';
import { Row, Col, Card, ListGroup, Button, Container } from "react-bootstrap";
import './Planes.css'

class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            listComentarios: [],
            id: "",
            title: ""
        };
        this.handleAddRisk = this.handleAddRisk.bind(this)
    }
    handleAddRisk() {
        // Simple POST request with a JSON body using fetch
        /*const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ title: 'React POST Request Example' })
        };
        fetch('https://jsonplaceholder.typicode.com/posts', requestOptions)
            .then(response => response.json())
            .then(data => this.setState({ postId: data.id }));*/
        //alert("xdxd")
        let newList = this.state.listComentarios
        newList.push({ comentario: "Hola mundo", id: 1 })
        this.setState({ listComentarios: newList });
    }
    componentDidMount() {
        // Simple POST request with a JSON body using fetch
        /*const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ title: 'React POST Request Example' })
        };
        fetch('https://jsonplaceholder.typicode.com/posts', requestOptions)
            .then(response => response.json())
            .then(data => this.setState({ postId: data.id }));*/
        this.setState({
            listComentarios: [{ comentario: "Hola mundo", id: 1 }, { comentario: "xwdwed", id: 2 }, { comentario: "xdxdxd", id: 3 }, { comentario: "xdxdxd", id: 4 }],
            id: "232424234",
            title: "Plan de calles"
        })
    }
    render() {
        return (
            <main>
                <div className="container-fluid">
                    <Row className="Content">
                        <Col md={3} sm={3}>
                            <Card>
                                <Card.Body>
                                    <center className="Comentarios">Comentarios</center>
                                    <ListGroup variant="flush">
                                        {this.state.listComentarios.map((i) => {
                                            return (
                                                <ListGroup.Item key={i.id}>
                                                    {i.comentario}
                                                </ListGroup.Item>
                                            )
                                        })}
                                    </ListGroup>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col md={9} sm={9}>
                            <Row>
                                <Col>
                                    <Button size="sm" onClick={this.handleAddRisk} className="boton" variant="primary">Agregar un riesgo</Button>{' '}
                                </Col>
                                <Col>
                                    <Button size="sm" className="boton" variant="primary">Agregar involucradas</Button>{' '}
                                </Col>
                                <Col>
                                    <Button size="sm" className="boton" variant="warning">Matriz de riesgos</Button>{' '}
                                </Col>
                                <Col>
                                    <Button size="sm" className="boton" variant="success">Matriz de riesgos</Button>{' '}
                                </Col>
                                <Col>
                                    <Button size="sm" className="boton" variant="light">Generar reporte</Button>{' '}
                                </Col>
                            </Row>
                            <Row>
                                {/*{this.state.title}-{"algo"}*/}
                                <span>&nbsp; </span>
                                <Container>
                                    <Row>
                                        <div class="card text-center">
                                            <div class="card">
                                                <ul class="nav nav-pills card-header-pills">
                                                <li class="nav-item">
                                                        <a class="nav-link info" href="#">                                         
                                                            <button type="button" class="btn btn-outline-secondary">Riesgos</button>
                                                        </a>
                                                    </li>
                                                    <span>&nbsp; </span>
                                                    <li class="nav-item">
                                                        <a class="nav-link info" href="#">                                         
                                                            <button type="button" class="btn btn-outline-secondary">Insidencia Asociada</button>
                                                        </a>
                                                    </li>
                                                    <span>&nbsp; </span>
                                                    <li class="nav-item">
                                                        <a class="nav-link info" href="#">                                         
                                                            <button type="button" class="btn btn-outline-secondary">Lista de Involucrados</button>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div class="card-body">
                                                sample text
                                            </div>
                                        </div>
                                    </Row>
                                </Container>
                            </Row>
                        </Col>
                    </Row>
                </div>
            </main>
        );
    }
}
export default Plan;