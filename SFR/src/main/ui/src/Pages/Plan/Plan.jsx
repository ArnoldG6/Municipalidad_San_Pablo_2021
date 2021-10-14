import React, { Component } from 'react';
import './Plan.css'
import { Row, Card, Nav } from "react-bootstrap";
import CommentSideBar from './Components/CommentSideBar';
import TopButtons from './Components/TopButtons';


class Plan extends Component {
    constructor(props) {
        super(props);
        this.state = {
            listComentarios: [],
            id: "",
            title: ""
        };
    }

    componentDidMount() {
        let query = new URLSearchParams(this.props.location.search);
        console.log(query.get('id'));
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
                        <TopButtons />
                    </Row>

                    {/* Datos del Plan */}
                    <Row className="mt-4">
                        <h1>Nombre del plan</h1>
                        <h2>Tipo-ID20211234</h2>
                        <h4>xx/xx/xxxx</h4>
                        <h4>Ricardo Milos</h4>
                        <p>ontent of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their i</p>
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