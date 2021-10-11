import { render } from '@testing-library/react';
import React, { Component } from 'react';
import { Button, Nav, ListGroup, Row, Col, Table, Container, Modal } from "react-bootstrap";
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

                    <Button id="NewItemButton" size="sm" onClick={this.openModal}>Crear Item</Button>
                    <form action="" class="algo">
                        <input type="text" id="fname" name="buscadfadfr" placeholder="Buscar"></input>
                    </form>




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
                                <td><a href="/home">Ricardo antonio</a></td>
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

function search() {
    return 1;


}





export default Planes;
/*
var Plan = {nombre: 'xd', fecha: 123, estado: 'xd', autor: "Esteban1"};
function addPlan() {
    let request = new Request('http://localhost:3000/:/SFR/' + 'Plans', {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(Plan)});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
          
            return;
        }
        document.location = 'http://localhost:8080/:/SFR/';
    })();
}
*/

