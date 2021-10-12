import React, { Component } from 'react';
import { Modal, Button } from "react-bootstrap";

class AddPlanModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            plan: { nombre: "", id: "", estado: "", autor: "" }
        };
        this.handleAddPlan = this.handleAddPlan.bind(this)
    }

    handleAddPlan() {
        //let newPlan = this.state.plan
        //this.setState({plan: newPlan});
        let newList = this.state.planes
        newList.push(this.state.plan);
        this.setState({ planes: newList });
    };


    render() {
        let render = this.props.show
        let closeModal = this.props.closeModal
        return (
            <Modal show={render} onHide={closeModal} >
                <Modal.Header>
                    Nuevo Item
                </Modal.Header>
                <Modal.Body>
                    <form>
                        <div class="form-group">
                            <label>Nombre: &nbsp;&nbsp;</label>
                            <input type="text" placeholder="Nombre" class="form-control" value={this.state.plan.nombre} onChange={(e) => this.setState({ plan: { nombre: e.target.value } })} />
                        </div>
                        <div class="form-group">
                            <label>ID:</label>
                            <input  type="text" class="form-control" placeholder="ID" value={this.state.plan.id} onChange={(e) => this.setState({ plan: { id: e.target.value } })} />
                        </div>
                        <div class="form-group">
                            <label>Estado: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <input type="text" placeholder="Estado" class="form-control" value={this.state.plan.estado} onChange={(e) => this.setState({ plan: { estado: e.target.value } })} />
                        </div>
                        <div class="form-group">
                            <label>Autor: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <input type="text" placeholder="Autor" class="form-control" value={this.state.plan.autor} onChange={(e) => this.setState({ plan: { autor: e.target.value } })} />
                        </div>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={this.handleAddPlan}>
                        Save Changes
                    </Button>
                    <Button variant="secondary" onClick={closeModal}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
};
export default AddPlanModal;
