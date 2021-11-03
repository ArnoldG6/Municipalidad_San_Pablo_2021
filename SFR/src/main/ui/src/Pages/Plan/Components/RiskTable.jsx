import React, { Component } from 'react';
import { Table, Button } from "react-bootstrap";
import AddExistingRiskModal from './AddExistingRiskModal';
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';

class RiskTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riesgos: [],
            showRiskModal: false,
            showDel: false,
            delID: ""
        };
        this.removeRisk = this.removeRisk.bind(this);
        this.openModalAddRisk = this.openModalAddRisk.bind(this);
        this.closeModalAddRisk = this.closeModalAddRisk.bind(this);
        this.openModalDelRisk = this.openModalDelRisk.bind(this);
        this.closeModalDelRisk = this.closeModalDelRisk.bind(this);
    }

    removeRisk() {
        this.props.removeRisks(this.state.delID);
        this.closeModalDelRisk();
    }

    openModalAddRisk() {
        this.setState({ showRiskModal: true });
    };

    closeModalAddRisk() {
        this.setState({ showRiskModal: false });
    };

    openModalDelRisk(id) {
        this.setState({ showDel: true, delID: id });
    };

    closeModalDelRisk() {
        this.setState({ showDel: false, delID: "" });
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
                                                    onClick={() => this.openModalDelRisk(risk.id)}
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
                    addRisk={this.props.addRisk}
                    show={this.state.showRiskModal}
                    closeModal={this.closeModalAddRisk} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelRisk}
                    action={this.removeRisk}
                    header={"Eliminar Riesgo de un Plan"}
                    body={"¿Esta seguro que desea eliminar este riesgo del Plan?"} />
            </div>
        );
    }
};
export default RiskTable;