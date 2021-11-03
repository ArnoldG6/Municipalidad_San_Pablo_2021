import React, { Component } from 'react';
import { Table, Button } from "react-bootstrap";
import AddExistingRiskModal from './AddExistingRiskModal';

class RiskTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riesgos: [],
            show: false
        };
        this.removeRisk = this.removeRisk.bind(this);
        this.openModalAddRisk = this.openModalAddRisk.bind(this);
        this.closeModalAddRisk = this.closeModalAddRisk.bind(this);
    }

    removeRisk(id) {
        this.props.removeRisks(id);
    }

    openModalAddRisk() {
        this.setState({ show: true });
    };

    closeModalAddRisk() {
        this.setState({ show: false });
    };

    render() {
        return (
            <div>
                <Table hover>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Tipo General</th>
                            <th>Tipo por Área</th>
                            <th>Tipo Específico</th>
                            <th><Button size="sm" onClick={this.openModalAddRisk} variant="success">Agregar Riesgo</Button></th>
                        </tr>
                    </thead>
                    {(typeof this.props.riesgos === 'undefined' || this.props.riesgos === null) ? <h1>No se han agregado riesgos</h1> :
                        this.props.riesgos.length === 0 ? <h1>No se han agregado riesgos</h1> :
                            <tbody>
                                {this.props.riesgos.map((risk) => {
                                    return (
                                        <tr key={risk.id}>
                                            <td>{risk.id}</td>
                                            <td>{risk.name}</td>
                                            <td>{risk.generalType}</td>
                                            <td>{risk.areaType}</td>
                                            <td>{risk.specType}</td>
                                            <td>
                                                <Button variant={sessionStorage.getItem("userRol") === "USER" ? "outline-dark" : "outline-danger"}
                                                    onClick={() => this.removeRisk(risk.id)}
                                                    disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>Eliminar</Button>
                                            </td>
                                        </tr>
                                    )
                                })}
                            </tbody>
                    }
                </Table>

                <AddExistingRiskModal
                    risks={this.props.availableRisks}
                    planID={this.props.planID}
                    show={this.state.show}
                    closeModal={this.closeModalAddRisk} />
            </div>
        );
    }
};
export default RiskTable;