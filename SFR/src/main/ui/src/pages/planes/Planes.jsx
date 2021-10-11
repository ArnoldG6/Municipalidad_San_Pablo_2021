import React, {Component} from 'react';
import {Card, Nav, ListGroup, Row, Col, Table, Container, Link} from "react-bootstrap";
import './plans.css';
class Planes extends Component {
    render() {
        return (           
                <Row className="Content">
                <Col md={1} sm={3} className="Sidebar">

                <Nav defaultActiveKey="/home" className="flex-column">
                    <Nav.Link href="/home">Proyectos</Nav.Link>
                    <Nav.Link href="/home">Procesos</Nav.Link>
                </Nav>

                </Col>
                <Col md={11} sm={9}>
                    
                <Table hover>
                            <thead>
                                <tr>
                                <th>Nombre</th>
                                <th>ID</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                                <th>Autor</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                <td><a  href="/home">Ricardo antonio</a></td>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td><div class="state">xD</div></td>
                                <td>xD</td>
                                </tr>
                                <tr>
                                <td>2</td>
                                <td>Jacob</td>
                                <td>Thornton</td>
                                <td>@fat</td>
                                <td>xD</td>
                                </tr>
                                
                            </tbody>
                            </Table>

       
                        
                
                </Col>
                </Row>
                );
    }
};



export default Planes;
/*
<ListGroup variant="flush">
                        <ListGroup.Item>Cras justo odio</ListGroup.Item>
                        <ListGroup.Item>Dapibus ac facilisis in</ListGroup.Item>
                        <ListGroup.Item>Vestibulum at eros</ListGroup.Item>
                    </ListGroup> 
*/