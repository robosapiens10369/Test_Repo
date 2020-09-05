package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.MecanumHardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="[ACTIVE] Mecanum Motion", group="K9bot")
public class MecanumDrive_MK2 extends LinearOpMode {

    /* Declare OpMode members. */
    MecanumHardware   robot           = new MecanumHardware();


    /* ---------------------------------------------------------------------------------------------
    double          armPosition     = robot.ARM_HOME;                   // Servo safe position
    double          clawPosition    = robot.CLAW_HOME;                  // Servo safe position
    final double    CLAW_SPEED      = 0.01 ;                            // sets rate to move servo
    final double    ARM_SPEED       = 0.01 ;                            // sets rate to move servo
    ---------------------------------------------------------------------------------------------*/

    public void setGrabber(double position) {

        robot.Servo0.setPosition(position);

    }

    boolean changed = false, on = false; //Outside of loop()



    @Override
    public void runOpMode() {
        double Drive;
        double Turn;
        double HStrafe;
        double VStrafe;
        double Arm;
        int ElbowPos;
        int HeightPos;
        int FirstTime;





        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        ElbowPos = 0;
        HeightPos = 0;
        FirstTime = 0;

        // Initialize servo positions
        setGrabber(0);
        robot.Servo3.setPosition(0);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Initialize motors for arms and reset encoders
        robot.ElbowMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.ElbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.HeightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.HeightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


          // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            Drive = -gamepad1.left_stick_y;
            Turn = -0.5 * gamepad1.left_stick_x;
            HStrafe = gamepad1.right_stick_x;
            VStrafe = -gamepad1.right_stick_y;


            //--------------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------------
            // Driving using IF statements to make sure that we are not conflicting

            // Drive and turn
            if ((Drive < -0.2 || Drive > 0.2) || (Turn < -0.2 || Turn > 0.2)) {
                robot.leftFront.setPower(Drive - Turn);
                robot.leftBack.setPower(Drive - Turn);
                robot.rightFront.setPower(Drive + Turn);
                robot.rightBack.setPower(Drive + Turn);

            //Drive and Strafe
            } else if ((HStrafe < -0.2 || HStrafe > 0.2) || (VStrafe < -0.2 || VStrafe > 0.2)) {
                robot.leftFront.setPower(HStrafe + VStrafe);
                robot.rightBack.setPower(HStrafe + VStrafe);
                robot.leftBack.setPower(VStrafe - HStrafe);
                robot.rightFront.setPower(VStrafe - HStrafe);

            //Stop
            } else {
                robot.leftFront.setPower(0);
                robot.leftBack.setPower(0);
                robot.rightFront.setPower(0);
                robot.rightBack.setPower(0);
            }

            //------------------------------------------------------------------------------------
            /* setGrabber is the variable that has the servo labeled to it.  When the A button is pressed,
            the servo activates under the setGrabber name, closing. When the A button is pressed AGAIN, the servos
            open.
            */

            if (gamepad1.a && !changed) {
                setGrabber(on ? 1 : 0);
                on = !on;
                changed = true;
            } else if (!gamepad1.a) changed = false;

            // Use the dpad left and right to move the actuator motor in and out
            if (gamepad1.dpad_right)
                robot.ActuatorMotor.setPower(1);
            else if (gamepad1.dpad_left)
                robot.ActuatorMotor.setPower(-1);
            else {
                robot.ActuatorMotor.setPower(0);
            }

            /* Arm code for ElbowMotor to stay at zero position */
            robot.ElbowMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.ElbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.ElbowMotor.setTargetPosition(ElbowPos);

            if (robot.ElbowMotor.isBusy()) {
                robot.ElbowMotor.setPower(0.7);

            }else {
                robot.ElbowMotor.setPower(0);
            }


            //--------------------------------------------------------------------------------------
            /* Arm code for HeightMotor, which raises the arm up and down using dpad*/
            //--------------------------------------------------------------------------------------

            // Move up if the dpad is up
            if (gamepad1.dpad_up) {
                robot.HeightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.HeightMotor.setPower(0.5);
                FirstTime = 1;
            }

            //Move down if dpad is pressed down, but set power low and let gravity do the rest
            else if (gamepad1.dpad_down)  {
                    robot.HeightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.HeightMotor.setPower(-0.02);
                    FirstTime = 1;
                }

            // Otherwise, hold position.  If this is the first time through the loop, read the current encoder position
            else{
                robot.HeightMotor.setPower(0);
                if (FirstTime == 1){
                    robot.HeightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    robot.HeightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    HeightPos = robot.HeightMotor.getCurrentPosition();
                    FirstTime = 0;
                    }

                    robot.HeightMotor.setTargetPosition(HeightPos);
                    if (robot.HeightMotor.isBusy()) {
                        robot.HeightMotor.setPower(1);

                    } else {
                        robot.HeightMotor.setPower(0);
                    }
                }


            //Report the encoder values to the user
            telemetry.addData("encoder-elbow", robot.ElbowMotor.getCurrentPosition());
            telemetry.addData("encoder-height", robot.HeightMotor.getCurrentPosition());
            telemetry.addData("encoder-actuator", robot.ActuatorMotor.getCurrentPosition());
            telemetry.addData("encoder-height-target", HeightPos);
            telemetry.update();


            //--------------------------------------------------------------------------------------
            /* Arm code for HeightMotor, which raises the arm up and down
            robot.HeightMotor = hardwareMap.dcMotor.get("HeightMotor");
            robot.HeightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.HeightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //while (HeightMotor.getCurrentPosition() != 0) {
            //idle();
            //}
            robot.HeightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.HeightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            int HeightUpPos = 200;
            int HeightDownPos = 0;
            int Htarget = 0;
            //--------------------------------------------------------------------------------------

             Arm code for ElbowMotor that acts like an elbow would
            robot.ElbowMotor = hardwareMap.dcMotor.get("ElbowMotor");
            robot.ElbowMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.ElbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //while (HeightMotor.getCurrentPosition() != 0) {
            //idle();
            //
            robot.ElbowMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.ElbowMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            int ElbowUpPos = 600;
            int ElbowDownPos = 0;
            int Etarget = 0;


            // Lines of code below is for the HeightMotor, that raises the arm and holds positions
            if (gamepad1.y) {
                robot.HeightMotor.setTargetPosition(HeightDownPos);
                Htarget = HeightUpPos;
                robot.ElbowMotor.setTargetPosition(ElbowDownPos);
                Etarget = ElbowDownPos;


            } else if (gamepad1.a) {
                robot.HeightMotor.setTargetPosition(HeightUpPos);
                Htarget = HeightDownPos;
                robot.ElbowMotor.setTargetPosition(ElbowUpPos);
                Etarget = ElbowUpPos;
            }

            if (robot.HeightMotor.isBusy()) {
                robot.HeightMotor.setPower(0.4);

            }else {
                robot.HeightMotor.setPower(0);
            }

            if (robot.ElbowMotor.isBusy()) {
                robot.ElbowMotor.setPower(0.4);

            }else {
                robot.ElbowMotor.setPower(0);
            }


            //Report the encoder values to the user
            // telemetry.addData("encoder-elbow", ElbowMotor.getCurrentPosition());
            telemetry.addData("encoder-height", robot.HeightMotor.getCurrentPosition());
            telemetry.addData("target", Htarget);
            telemetry.update();

            Lines of code below is for the ElbowMotor
            if (gamepad1.b) {
                robot.ElbowMotor.setTargetPosition(ElbowUpPos);
                Etarget = ElbowUpPos;
            } else if (gamepad1.x) {
                robot.ElbowMotor.setTargetPosition(ElbowDownPos);
                Etarget = ElbowDownPos;
            }
            if (robot.ElbowMotor.isBusy()) {
                robot.ElbowMotor.setPower(0.4);
            } else {
                robot.ElbowMotor.setPower(0);
            }



            //Report the encoder values to the user
            // telemetry.addData("encoder-elbow", ElbowMotor.getCurrentPosition());
            telemetry.addData("encoder-elbow", robot.ElbowMotor.getCurrentPosition());
            telemetry.addData("target", Etarget);
            telemetry.update();
            */
            //--------------------------------------------------------------------------------------

            /* This section controls the servo on the back of the robot that latches onto the
            building platform through the B button on the Gamepad. It works by press-n-hold, holding
            the button keeps the servo activated and latched, releasing the button releases the latch
            */

            if (gamepad1.b) {
                robot.Servo2.setPosition(1);
            } else {
                robot.Servo2.setPosition(0);
            }

            /* Servo3 refers to the servo that acts as the break for the arm of the robot. When
            activated, the servo pushes a gear into the chains and locks everything in place; When
            deactivated, the servo moves the gear out of the chain drive.
             */


            // Pause for 10 mS each cycle = update 100 times a second.
            sleep(10);
        }
        }
    }

