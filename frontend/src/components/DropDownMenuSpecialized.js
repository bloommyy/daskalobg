import { DropDownList } from "@progress/kendo-react-dropdowns";
import { DropDownType } from '../utils'

const MARKS = 'mark'
const ABSENCES = 'date'
const FEEDBACKS = 'description'

export const DropDownMenuSpecialized = ({ values, type, onChange }) => {

    let field
    switch (type) {
        case DropDownType.Marks:
            field = MARKS;
            break;
        case DropDownType.Absences:
            field = ABSENCES;
            break;
        case DropDownType.Feedbacks:
            field = FEEDBACKS;
            break;

        default:
            alert("No such dropdown type.")
            break;
    }

    return (
        <section className="k-my-8">
            <form className="k-form">
                <DropDownList data={values} textField={field} dataItemKey="id" onChange={e => onChange(e.value)} />
            </form>
        </section>
    );
};